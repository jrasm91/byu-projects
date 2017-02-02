#pragma once


#ifndef BUFFER_H_
#define BUFFER_H_

#include <queue>
#include <semaphore.h>

using namespace std;

class ClientBuffer {

public:
	ClientBuffer();

	void enqueue(int clientId);
	int dequeue();
	void printBuffer();

private:
	queue<int> buffer;
	sem_t SEM_BUF_S;
	sem_t SEM_BUF_E;
	sem_t SEM_BUF_N;
};



#endif /* BUFFER_H_ */
