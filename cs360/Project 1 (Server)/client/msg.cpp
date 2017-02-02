#include "msg.h"
#include <regex.h>
#include <stdlib.h>
#include <unistd.h>
#include <iostream>
#include <sstream>

int main(int argc, char **argv) {
	int option;
	// setup default arguments
	int port = 3000;
	string host = "localhost";
	bool debug = false;

	// process command line options using getopt()
	// see "man 3 getopt"
	while ((option = getopt(argc,argv,"h:p:")) != -1) {
		switch (option) {
		case 'p':
			port = atoi(optarg);
			break;
		case 'h':
			host = optarg;
			break;
		case 'd':
			debug = true;
			break;
		default:
			cout << "client [-h host] [-p port]" << endl;
			exit(EXIT_FAILURE);
		}
	}
	printf("Starting Client(host='%s', port='%d')\n", host.c_str(), port);
	Client client = Client(host, port, debug);
}

void Client::listen() {
	string line;
	string response = "";
	string request = "";

	cout << "% ";

	// loop to handle user interface
	while (getline(cin,line)) {
		request = parse_command(line);
		if(request.size() == 0){
			cout << "% ";
			continue;
		}
		bool success = send_request(request);
		// break if an error occurred
		if (not success)
			break;
		// get a response
		success = get_response();
		// break if an error occurred
		if (not success)
			break;
		cout << "% ";

	}
	close(server_);
}

string Client::parse_command(string line){
	string request = "";

	string RGX_SEND = "send [a-zA-Z0-9]\\+ .\\+";
	string RGX_LIST = "list [a-zA-Z0-9]\\+";
	string RGX_READ = "read [a-zA-Z0-9]\\+ [0-9]\\+";
	string RGX_QUIT = "quit";

	if(line.find("send") == 0 && match(line, RGX_SEND)){
		if(debug_) printf("send: '%s'\n", line.c_str());

		line = line.substr(line.find(" ") + 1);
		string name = line.substr(0, line.find(" "));
		line = line.substr(line.find(" ") + 1);
		string subject = line;
		string message = read_message();
		int length = message.size();

		if(debug_) printf("N[%s] S[%s] L[%d] M[%s]\n", name.c_str(), subject.c_str(), length, message.c_str());
		stringstream ss;
		ss << "put " << name << " " << subject << " " << length << "\n" << message;
		request = ss.str();

	} else if(line.find("list") == 0 && match(line, RGX_LIST)) {
		if(debug_) printf("list: '%s'\n", line.c_str());
		request = line + "\n";
		if(debug_) printf("R[%s]\n", request.c_str());
	} else if(line.find("read") == 0 && match(line, RGX_READ)) {
		if(debug_) printf("read: '%s'\n", line.c_str());
		line = line.substr(line.find(" ") + 1);
		string user = line.substr(0, line.find(" "));
		line = line.substr(line.find(" ") + 1);
		int index = atoi(line.c_str());
		stringstream ss;
		ss << "get " << user << " " << index << "\n";
		request = ss.str();
		if(debug_) printf("R[%s]\n", request.c_str());
	} else if(line.find("quit") == 0 && match(line, RGX_QUIT)){
		if(debug_) printf("Q[%s]\n", line.c_str());
		printf("bye\n");
		exit(0);
	} else {
		printf("Not a command! [%s]\n", line.c_str());
	}
	return request;
}

bool Client::match(string req, string exp){
	regex_t regex;
	regcomp(&regex, exp.c_str(), 0);
	return !regexec(&regex, req.c_str(), 0, NULL, 0);
}

string Client::read_message(){
	string line;
	string msg = "";

	printf("- Type your message. End with a blank line -\n");

	// loop to handle user interface
	while (getline(cin,line)){
		if(line.size() == 0)
			break;
		msg += line + "\n";
	}
	return msg.substr(0, msg.size() - 1);
}

string Client::format_message(string msg){



	return msg;
}

bool Client::send_request(string request) {
	// prepare to send request
	const char* ptr = request.c_str();
	int nleft = request.length();
	int nwritten;
	// loop to be sure it is all sent
	while (nleft) {
		if ((nwritten = send(server_, ptr, nleft, 0)) < 0) {
			if (errno == EINTR) {
				// the socket call was interrupted -- try again
				continue;
			} else {
				// an error occurred, so break out
				perror("write");
				return false;
			}
		} else if (nwritten == 0) {
			// the socket is closed
			return false;
		}
		nleft -= nwritten;
		ptr += nwritten;
	}
	return true;
}

bool Client::get_response() {
	string response = "";
	// read until we get a newline
	while (response.find("\n") == string::npos) {
		int nread = recv(server_,buf_,1024,0);
		if (nread < 0) {
			if (errno == EINTR)
				// the socket call was interrupted -- try again
				continue;
			else
				// an error occurred, so break out
				return "";
		} else if (nread == 0) {
			// the socket is closed
			return "";
		}
		// be sure to use append in case we have binary data
		response.append(buf_,nread);
	}
	// a better client would cut off anything after the newline and
	// save it in a cache
	if(response.find("message") == 0){
		response = response.substr(response.find(" ") + 1);
		string subject = response.substr(0, response.find(" ") + 1);
		response = response.substr(response.find("\n") + 1);
		string message = response;
		cout << subject << endl << message << endl;
	}
	else
		cout << response;
	return true;
}



Client::Client(string host, int port, bool debug) {
	// setup variables
	host_ = host;
	port_ = port;
	buflen_ = 1024;
	buf_ = new char[buflen_+1];
	debug_ = debug;

	// connect to the server and run echo program
	create();
	listen();
}

Client::~Client() {
}

void Client::create() {
	struct sockaddr_in server_addr;

	// use DNS to get IP address
	struct hostent *hostEntry;
	hostEntry = gethostbyname(host_.c_str());
	if (!hostEntry) {
		cout << "No such host name: " << host_ << endl;
		exit(-1);
	}

	// setup socket address structure
	memset(&server_addr,0,sizeof(server_addr));
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(port_);
	memcpy(&server_addr.sin_addr, hostEntry->h_addr_list[0], hostEntry->h_length);

	// create socket
	server_ = socket(PF_INET,SOCK_STREAM,0);
	if (!server_) {
		perror("socket");
		exit(-1);
	}

	// connect to server
	if (connect(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
		perror("connect");
		exit(-1);
	}
}
