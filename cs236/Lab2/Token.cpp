
#include "Token.h"

using namespace std;

Token::Token(){
	value = "";
	line_num = 0;
	token_type = "";
}

Token::Token(string avalue, int aline_num, string atoken_type) {
	value = avalue;
	line_num = aline_num;
	token_type = atoken_type;
}

string Token::getType(){
	return token_type;
}

string Token::getValue(){
	return value;
}

string Token::toString() {
	stringstream ss;
	ss << "(" << token_type;
	ss << ",\"" << value << "\"," ;
	ss << line_num << ")" << endl;
	return ss.str();
}


