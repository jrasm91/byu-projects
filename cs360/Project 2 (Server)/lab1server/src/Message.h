/*
 * Message.h
 *
 *  Created on: Sep 23, 2013
 *      Author: jr2of6
 */

#ifndef MESSAGE_H_
#define MESSAGE_H_

#include <string>


using namespace std;

class Message{

public:
	Message(string name, string subject, string body, int length);

	string getName();
	string getSubject();
	string getBody();
	int getLength();
	string toString();



private:
	string name;
	string subject;
	string body;
	int length;
};





#endif /* MESSAGE_H_ */
