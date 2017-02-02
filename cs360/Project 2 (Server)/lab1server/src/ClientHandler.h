#ifndef CLIENTHANDLER_H_
#define CLIENTHANDLER_H_

#include <arpa/inet.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <unistd.h>

#include "Message.h"
#include "MessageDatabase.h"
#include "ClientBuffer.h"
#include "Server.h"

using namespace std;

//extern struct SHARED_DATA data;
class ClientHandler{


public:
	ClientHandler(SharedData* data);
	~ClientHandler();
	void handle(int clientId);
	void handle();

private:

	string parseRequest(string request);
	string listMessages(string);
	string getMessage(string);
	static Message parseMessage(string msg);

	string getRequest(int clientId);
	string getRestOfRequest(int, string);
	string getRequestLength(int clientId, int length, string msgSoFar);
	bool sendResponse(int, string);


	MessageDatabase* messageMap;
	ClientBuffer* clientBuffer;
	sem_t* SEM_READ;
	int threadId;
	int buflen_;
	char* buf_;
	bool debug;

};

#endif /* CLIENTHANDLER_H_ */
