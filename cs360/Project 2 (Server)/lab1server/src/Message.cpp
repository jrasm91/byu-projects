
#include "Message.h"
#include <stdio.h>
#include <vector>
#include <map>
#include <iostream>

Message::Message(string name, string subject, string body, int length){
	this->name = name;
	this->subject = subject;
	this->body = body;
	this->length = length;
}

string Message::getSubject(){
	return subject;
}

string Message::getBody(){
	return body;
}

string Message::getName(){
	return name;
}

int Message::getLength(){
	return length;
}

string Message::toString(){
	char buffer[1024];
	sprintf(buffer, "Made Message(name='%s', subject='%s', length='%d', body='%s')\n",
				name.c_str(),
				subject.c_str(),
				length,
				body.c_str());
	return buffer;
}

