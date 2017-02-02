#include <stdlib.h>
#include <unistd.h>
#include <iostream>
#include <sstream>
#include <regex.h>
#include "msgd.h"

using namespace std;

int main(int argc, char **argv) {
	int option, port;

	// setup default arguments
	port = 3000;

	// process command line options using getopt()
	// see "man 3 getopt"
	while ((option = getopt(argc,argv,"p:")) != -1) {
		switch (option) {
		case 'p':
			port = atoi(optarg);
			break;
		default:
			cout << "server [-p port]" << endl;
			exit(EXIT_FAILURE);
		}
	}
	printf("Starting Server(port='%d')\n", port);
	Server server = Server(port);
}

Server::Server(int port) {
	// setup variables
	port_ = port;
	buflen_ = 1024;
	buf_ = new char[buflen_+1];

	messageMap = map<string, vector<string> >();

	// create and run the server
	create();
	serve();
}
//
void Server::serve() {
	// setup client
	int client;
	struct sockaddr_in client_addr;
	socklen_t clientlen = sizeof(client_addr);

	// accept clients
	while ((client = accept(server_,(struct sockaddr *)&client_addr,&clientlen)) > 0) {
		handle(client);
		close(client);
	}
	close(server_);
}

void Server::handle(int client) {
	// loop to handle all requests
	while (1) {
		string request = get_header(client);
		request = get_body(client, request);
		string response = parse_request(request);
		bool success = send_response(client,response);
		if (not success)
			break;
	}
}

string Server::parse_request(string request){
	string MSG_ERR = "error not valid syntax\n";
	string MSG_OK = "OK\n";

	string RGX_MSG = "put \\w\\+ .\\+ [0-9]\\+\n\\w\\+";
	string RGX_RST = "reset\n";
	string RGX_LST = "list \\w\\+";
	string RGX_GET = "get \\w\\+ [0-9]\\+";

	string response;

	if(match(request, RGX_MSG)){
		store_message(request);
		response = MSG_OK;
	} else if(match(request, RGX_LST)){
		string name = parse_name(request);
		response = list_messages(name);
	} else if(match(request, RGX_RST)){
		response = MSG_OK;
	}  else if(match(request, RGX_GET)){
		response = get_message(request);
	} else{
		response = MSG_ERR;
	}
	return response;
}

bool Server::match(string req, string exp){
	regex_t regex;
	regcomp(&regex, exp.c_str(), 0);
	return !regexec(&regex, req.c_str(), 0, NULL, 0);
}

void Server::store_message(string input){
	input = input.substr(input.find(" ") + 1);
	string name = input.substr(0, input.find(" "));
	string message = input.substr(input.find(" ") + 1);
	messageMap[name].push_back(message);
}

string Server::parse_name(string input){
	input = input.substr(input.find(" ") + 1);
	input = input.substr(0, input.size() - 1);
	string name = input;
	return name;
}

string Server::get_message(string request){
	string MSG_ERR = "error no message with that index\n";

	request = request.substr(request.find(" ") + 1);
	string name = request.substr(0, request.find(" "));
	request = request.substr(request.find(" ") + 1);
	int index = atoi((request.substr(0, request.size() - 1)).c_str());

	if(!messageMap.count(name) || index > (int)messageMap[name].size())
		return MSG_ERR;

	return "message " + messageMap[name][index - 1];
}

string Server::get_body(int client, string request){
	string header = request.substr(0, request.find("\n"));
	int index = request.find("put");
	if(index != 0)
		return request;

	string length_s = header.substr(header.find_last_of(" "));
	int length = atoi(length_s.c_str());
	string body = request.substr(request.find("\n") + 1);
	if((int)body.length() >= length)
		return request;

	request = header + "\n" + get_request_length(client, length, body);
	return request;
}

string Server::list_messages(string name){
	string MSG_NONE = "list 0\n";

	if(!messageMap.count(name))
		return MSG_NONE;

	stringstream ss;
	ss << "list " << messageMap[name].size() << "\n";
	for(int i = 0; i < (int)messageMap[name].size(); i++){
		string msg = messageMap[name][i].c_str();
		msg = msg.substr(0, msg.find(" "));
		ss << (i + 1) << " " << msg << "\n";
	}
	return ss.str();
}
string Server::get_header(int client) {
	string request = "";
	string signal = "\n";
	// read until we get a newline
	while (request.find(signal) == string::npos) {
		int nread = recv(client,buf_,1024,0);
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
	return request;
}

string Server::get_request_length(int client, int length, string request){

	// read until we get a newline
	while ((int)request.length() < length) {
		int nread = recv(client,buf_,1024,0);
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
	return request;
}

bool Server::send_response(int client, string response) {
	// prepare to send response
	const char* ptr = response.c_str();
	int nleft = response.length();
	int nwritten;
	// loop to be sure it is all sent
	while (nleft) {
		if ((nwritten = send(client, ptr, nleft, 0)) < 0) {
			if (errno == EINTR) {
				// the socket call was interrupted -- try again
				continue;
			} else {
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
	return true;
}

Server::~Server() {
	delete buf_;
}

void
Server::create() {
	struct sockaddr_in server_addr;

	// setup socket address structure
	memset(&server_addr,0,sizeof(server_addr));
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(port_);
	server_addr.sin_addr.s_addr = INADDR_ANY;

	// create socket
	server_ = socket(PF_INET,SOCK_STREAM,0);
	if (!server_) {
		perror("socket");
		exit(-1);
	}

	// set socket to immediately reuse port when the application closes
	int reuse = 1;
	if (setsockopt(server_, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
		perror("setsockopt");
		exit(-1);
	}

	// call bind to associate the socket with our local address and port
	if (bind(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
		perror("bind");
		exit(-1);
	}

	// convert the socket to listen for incoming connections
	if (listen(server_,SOMAXCONN) < 0) {
		perror("listen");
		exit(-1);
	}
}
