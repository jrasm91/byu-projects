

#pragma once
#include <string>
#ifndef TOKEN_H_
#define TOKEN_H_

using namespace std;

class Token {

private:
	string str, token_type;
	int line_num;
public:
	Token(string astr, int aline_num, string atoken_type);
	string toString();
};

#endif /* TOKEN_H_ */
