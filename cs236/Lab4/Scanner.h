

#pragma once
#ifndef SCANNER_H_
#define SCANNER_H_
#include <vector>
#include <string>
#include <fstream>
#include <iostream>
#include <cctype>
#include "Token.h"
#include "LineStream.h"

using namespace std;

class Scanner {

private:
	void readLine(LineStream stream);
	void readFile(string fileName);
	vector<Token> tokens;
	int lineCounter;
	LineStream addBasicToken(LineStream stream);
	void addComplexToken(string str, int line_num);
	LineStream readString(LineStream stream);

public:
	Scanner(string filename);
	vector<Token> getTokens();

	virtual ~Scanner();
};

#endif /* SCANNER_H_ */
