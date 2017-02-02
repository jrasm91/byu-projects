

#include "MessageDatabase.h"
#include "stdio.h"
#include "sstream"

MessageDatabase::MessageDatabase(){
	messageMap = map<string, vector<Message> >();
	sem_init(&SEM_MDB, 0, 1);
}

void MessageDatabase::clear(){
	sem_wait(&SEM_MDB);
	messageMap.clear();
	sem_post(&SEM_MDB);
}

bool MessageDatabase::hasUser(string user){
	sem_wait(&SEM_MDB);
	bool count =  messageMap.count(user) != 0;
	sem_post(&SEM_MDB);
	return count;
}

string MessageDatabase::listMessages(string name){
	sem_wait(&SEM_MDB);
	stringstream ss;
	vector<Message> messages = messageMap[name];
	ss << "list " << messages.size() << "\n";
	for(int i = 0; i < (int)messages.size(); i++)
		ss << (i + 1) << " " << messages[i].getSubject() << "\n";
	sem_post(&SEM_MDB);
	string result = ss.str();
	return result;
}

vector<Message> MessageDatabase::getMessages(string user){
	sem_wait(&SEM_MDB);
	vector<Message> messages = messageMap[user];
	sem_post(&SEM_MDB);
	return messages;
}

void MessageDatabase::addMessage(Message& msg){
	sem_wait(&SEM_MDB);
	messageMap[msg.getName()].push_back(msg);
	sem_post(&SEM_MDB);
}

void MessageDatabase::printDatabase(void){
	sem_wait(&SEM_MDB);
	printf("\n***Printing Message Map***\n");
	typedef map<string, vector<Message> >::iterator it_type;
	for(it_type iterator = messageMap.begin(); iterator != messageMap.end(); iterator++) {
		string key = iterator->first;
		vector<Message> msgs = iterator->second;
		for(int i = 0; i < (int)msgs.size(); i++)
			printf("[%s][%d] --> Name='%s', Subject='%s', Body='%s'\n",
					key.c_str(),
					i,
					msgs[i].getName().c_str(),
					msgs[i].getSubject().c_str(),
					msgs[i].getBody().c_str());
	}
	printf("***Finished Printing Message Map***\n");
	sem_post(&SEM_MDB);
}


