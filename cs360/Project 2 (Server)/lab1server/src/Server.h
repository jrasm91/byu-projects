
#pragma once

#include "MessageDatabase.h"

#include "ClientBuffer.h"
#include <iostream>
#include <sstream>
#include <vector>
#include <map>
#include <string>

using namespace std;

typedef struct SharedData_ {
	MessageDatabase messageMap;
	ClientBuffer clientBuffer;
	int threadId;
	sem_t SEM_READ;
} SharedData;

class Server {
public:
	Server(int);
	~Server();

private:

	void initializeSocket(void);

	static void* makeClientHandler(void * sharedData);
	void makeWorkerThreads(void);
	void acceptClients(void);
	SharedData* sharedData;

	int port_;
	int server_;

	vector<pthread_t*> threads;
};
