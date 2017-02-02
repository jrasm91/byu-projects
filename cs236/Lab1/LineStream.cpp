
#include "LineStream.h"
#include <iostream>
using namespace std;

LineStream::LineStream(int line_number, string astream) {
	line_num = line_number;
	index = 0;
	stream = "";
	stream = astream;
}

int LineStream::getLineNumber(){
	return line_num;
}

char LineStream::getChar(){
	return stream[index];
}
char LineStream::advance(){
	index++;
	return stream[index];
}
char LineStream::peek(){
	return stream[index + 1];
}

LineStream:: ~LineStream(){

}

