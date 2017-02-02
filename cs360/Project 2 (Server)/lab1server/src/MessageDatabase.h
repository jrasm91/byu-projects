#pragma once

#ifndef MESSAGEMAP_H_
#define MESSAGEMAP_H_


#include "Message.h"

#include <map>
#include <vector>
#include <semaphore.h>

using namespace std;

class MessageDatabase{
public:

	MessageDatabase();

	void clear(void);
	bool hasUser(string user);
	string listMessages(string name);
	vector<Message> getMessages(string user);

	void addMessage(Message& msg);
	void printDatabase(void);
private:
	map<string, vector<Message> > messageMap;
	sem_t SEM_MDB;
};


#endif /* MESSAGEMAP_H_ */
