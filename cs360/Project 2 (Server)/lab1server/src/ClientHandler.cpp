

#include "ClientHandler.h"
#include "Utility.h"
#include "Server.h"

// messages for communication
string MSG_IND_ERR = "error no message with that index\n";
string MSG_SYN_ERR = "error not valid syntax\n";
string MSG_NONE = "list 0\n";
string MSG_OK = "OK\n";

// regular expressions for checking syntax
string RGX_MSG = "put \\w\\+ .\\+ [0-9]\\+\n\\w\\+";
string RGX_RST = "reset\n";
string RGX_LST = "list \\w\\+";
string RGX_GET = "get \\w\\+ [0-9]\\+";

ClientHandler::ClientHandler(SharedData* sd){
	this->messageMap = &sd->messageMap;
	this->clientBuffer = &sd->clientBuffer;
	this->threadId = sd->threadId;
	this->SEM_READ = &sd->SEM_READ;
	this->buflen_ = 1024;
	this->buf_ = new char[buflen_+1];
	this->debug = false;
	handle();
}

ClientHandler::~ClientHandler() {
	delete buf_;
}

void ClientHandler::handle(void){
	int client;
	while(true){
		client = clientBuffer->dequeue();
		handle(client);
		close(client);
	}
}

void ClientHandler::handle(int client) {
	// loop to handle all requests
	while (true) {
		string request = getRequest(client);
		if(request.length() == 0)
			break;
		request = getRestOfRequest(client, request);
		string response = parseRequest(request);
		bool success = sendResponse(client,response);
		if (not success)
			break;
	}
}

string ClientHandler::parseRequest(string request){
	if(debug) printf("[%02d] Received Request: '%s'\n", threadId, request.c_str());
	if(Utility::match(request, RGX_MSG)){
		Message msg = ClientHandler::parseMessage(request);
		messageMap->addMessage(msg);
		if(debug) messageMap->printDatabase();
		return MSG_OK;
	} else if(Utility::match(request, RGX_LST)){
		string name = Utility::getNthSplit(1, request);
		return listMessages(name);
	} else if(Utility::match(request, RGX_RST)){
		messageMap->clear();
		return MSG_OK;
	}  else if(Utility::match(request, RGX_GET)){
		return getMessage(request);
	} else{
		return MSG_SYN_ERR;
	}
}

string ClientHandler::listMessages(string name){
	if(!messageMap->hasUser(name))
		return MSG_NONE;
	return messageMap->listMessages(name);
}

string ClientHandler::getMessage(string request){
	string name = Utility::getNthSplit(1, request);
	int index = atoi(Utility::getNthSplit(2, request).c_str());
	if(!messageMap->hasUser(name))
		return MSG_IND_ERR;
	if(index > (int)messageMap->getMessages(name).size())
		return MSG_IND_ERR;
	Message msg = messageMap->getMessages(name)[index-1];
	stringstream ss;
	ss << "message " << msg.getSubject() << " " << msg.getLength() << "\n" << msg.getBody();
	return ss.str();
}

Message ClientHandler::parseMessage(string msg){
	string name = Utility::getNthSplit(1, msg);
	string subject = Utility::getNthSplit(2, msg);
	int length = atoi(Utility::getNthSplit(3, msg).c_str());
	string body = msg.substr(msg.find("\n") + 1);
	Message result = Message(name, subject, body, length);
	return result;
}

//**************************************//
// Methods to Send and Receive Messages //
//**************************************//

string ClientHandler::getRequest(int client) {
	string request = "";
	string signal = "\n";
	// read until we get a newline
	while (request.find(signal) == string::npos) {
//		sem_wait(SEM_READ);
		int nread = recv(client,buf_,1024,0);
//		sem_post(SEM_READ);
		if (nread < 0) {
			if (errno == EINTR)
				// the socket call was interrupted -- try again
				continue;
			else
				perror("get: ");
			// an error occurred, so break out
			return "";
		} else if (nread == 0) {
			// the socket is closed
			return "";
		}
		// be sure to use append in case we have binary data
		request.append(buf_,nread);
	}
	// a better server would cut off anything after the newline and
	// save it in a cache
	//	sem_post(SEM_READ);

	return request;
}

string ClientHandler::getRestOfRequest(int client, string request){
	string header = request.substr(0, request.find("\n"));
	int index = request.find("put");
	if(index != 0)
		return request;
	string length_s = header.substr(header.find_last_of(" ") + 1);
	int length = atoi(length_s.c_str());
	string body = request.substr(request.find("\n") + 1);
	if((int)body.length() >= length)
		return request;
	request = header + "\n" + getRequestLength(client, length, body);
	return request;
}

string ClientHandler::getRequestLength(int client, int length, string request){
	//	sem_wait(SEM_READ);
	// read until we get a newline
	while ((int)request.length() < length) {
//		sem_wait(SEM_READ);
		int nread = recv(client,buf_,1024,0);
//		sem_post(SEM_READ);
		if (nread < 0) {
			if (errno == EINTR)
				// the socket call was interrupted -- try again
				continue;
			else
				perror("get: ");
			// an error occurred, so break out
			return "";
		} else if (nread == 0){
			// the socket is closed
			return "";
		}
		// be sure to use append in case we have binary data
		request.append(buf_,nread);
	}
	//	sem_post(SEM_READ);

	return request;
}

bool ClientHandler::sendResponse(int client, string response) {
	//	sem_wait(SEM_READ);
	// prepare to send response
	if(debug) printf("Sending Response: '%s'\n", response.c_str());
	const char* ptr = response.c_str();
	int nleft = response.length();
	int nwritten;
	// loop to be sure it is all sent
	while (nleft) {
//		sem_wait(SEM_READ);
		nwritten = send(client, ptr, nleft, 0);
//		sem_post(SEM_READ);
		if ((nwritten) < 0) {
			if (errno == EINTR) {
				// the socket call was interrupted -- try again
				continue;
			} else {
				fprintf(stderr, "Response: '%s'\n", response.c_str());
				fprintf(stderr, "Error: '%d'\n", errno);
				// an error occurred, so break out
				perror("write: ");
				return false;
			}
		} else if (nwritten == 0) {
			perror("The socket was closed");
			// the socket is closed
			return false;
		}
		nleft -= nwritten;
		ptr += nwritten;
	}
	//	sem_post(SEM_READ);
	return true;
}
