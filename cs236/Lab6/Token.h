

#pragma once
#ifndef TOKEN_H_
#define TOKEN_H_
#include <string>
#include <sstream>

using namespace std;

class Token {
private:
	string value, token_type;
	int line_num;
public:
	Token();
	Token(string avalue, int aline_num, string atoken_type);
	string toString();
	string getType();
	string getValue();
};

#endif /* TOKEN_H_ */
