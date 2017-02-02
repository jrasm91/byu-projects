

#pragma once
#ifndef LEXICALANALYZER_H_
#define LEXICALANALYZER_H_
#include <string>
#include <vector>
#include "Token.h"
#include "LineStream.h"

using namespace std;

class LexicalAnalyzer {

private:
	void readLine(LineStream stream);
	void readFile(string fileName);
	vector<Token> tokens;
	int lineCounter;
	LineStream addBasicToken(LineStream stream);
	void addComplexToken(string str, int line_num);
	LineStream readString(LineStream stream);

public:
	LexicalAnalyzer(string filename);
	vector<Token> getTokens();

	virtual ~LexicalAnalyzer();
};

#endif /* LEXICALANALYZER_H_ */
