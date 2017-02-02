
#pragma once
#ifndef LINESTREAM_H_
#define LINESTREAM_H_
#include <string>

using namespace std;

class LineStream {
private:
	int line_num;
	int index;
	string stream;

public:
	LineStream(int aline_numb, string astream);
	int getLineNumber();
	char peek();
	char getChar();
	char advance();
	virtual ~LineStream();
};

#endif /* LINESTREAM_H_ */
