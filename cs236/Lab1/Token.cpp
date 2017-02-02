
#include "Token.h"
#include <string>
#include <sstream>


using namespace std;

Token::Token(string astr, int aline_num, string atoken_type) {
	str = astr;
	line_num = aline_num;
	token_type = atoken_type;
};

string Token::toString() {
	stringstream ss;
	ss << "(" << token_type;
	ss << ",\"" << str << "\"," ;
	ss << line_num << ")" << endl;
	return ss.str();
}

