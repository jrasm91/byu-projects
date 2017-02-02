

#include "ClientBuffer.h"
#include "stdio.h"

int BUFFER_SIZE = 1000;

ClientBuffer::ClientBuffer(){
	buffer = queue<int>();
	sem_init(&SEM_BUF_S, 0, 1);
	sem_init(&SEM_BUF_N, 0, 0);
	sem_init(&SEM_BUF_E, 0, BUFFER_SIZE);
}

void ClientBuffer::enqueue(int clientId){
	sem_wait(&SEM_BUF_E);
	sem_wait(&SEM_BUF_S);
//	printf("Added [%d] to Buffer\n", clientId);
	buffer.push(clientId);
	sem_post(&SEM_BUF_S);
	sem_post(&SEM_BUF_N);
}

int ClientBuffer::dequeue(){
	sem_wait(&SEM_BUF_N);
	sem_wait(&SEM_BUF_S);
	int clientId = buffer.front();
	buffer.pop();
//	printf("Removed [%d] from Buffer\n", clientId);
	sem_post(&SEM_BUF_S);
	sem_post(&SEM_BUF_E);
	return clientId;
}


void ClientBuffer::printBuffer(){
	sem_wait(&SEM_BUF_S);
//	printf("***Printing Buffer***\n");
//	for(int i = 0; i < (int)buffer.size(); i++)
//		printf("[%02d] --> '%d'\n", i, buffer[i]);
//	printf("***Finished Printing Buffer***\n");
	sem_post(&SEM_BUF_S);


}
