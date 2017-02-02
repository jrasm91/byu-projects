
#include "Scanner.h"

using namespace std;

Scanner::Scanner(string filename) {
	lineCounter = 1;
	readFile(filename);
}

void Scanner::readFile(string filename) {
	ifstream myInputFile;
	myInputFile.open(filename.data());
	if(myInputFile){
		char nextChar;
		string s = "";
		while(myInputFile.get(nextChar)){
			s += nextChar;
			if(nextChar == '\n'){
				readLine(LineStream(lineCounter, s));
				s = "";
				lineCounter++;
			}
		}
		readLine(LineStream(lineCounter, s + "\n"));
	}
	else
		cout << "Cannot open " + filename + "for reading" << endl;
}

void Scanner::readLine(LineStream stream){

	for(char current = stream.getChar(); current != '\n';){
		if(isspace(current))
			current = stream.advance();

		else if(isalpha(current)){
			stream  = readString(stream);
			current = stream.getChar();
		}

		else {
			stream = addBasicToken(stream);
			current = stream.getChar();
			current = stream.advance();
		}
	}
}

LineStream Scanner::readString(LineStream stream){
	char current = stream.getChar();
	string str = "";
	str = current;
	while(isalnum(current = stream.advance()))
		str += current;
	addComplexToken(str, lineCounter);
	return stream;
}

void Scanner::addComplexToken(string str, int line_num){

	string type;
	if(str == "Schemes")
		type = "SCHEMES";
	else if(str == "Facts")
		type = "FACTS";
	else if(str == "Rules")
		type = "RULES";
	else if(str == "Queries")
		type = "QUERIES";
	else
		type = "ID";

	tokens.push_back(Token(str, lineCounter, type));
}

LineStream Scanner::addBasicToken(LineStream stream){
	switch(stream.getChar()){
	case ',': tokens.push_back(Token(",", lineCounter, "COMMA")); break;
	case '.': tokens.push_back(Token(".", lineCounter, "PERIOD")); break;
	case '?': tokens.push_back(Token("?", lineCounter, "Q_MARK")); break;
	case '(': tokens.push_back(Token("(", lineCounter, "LEFT_PAREN")); break;
	case ')': tokens.push_back(Token(")", lineCounter, "RIGHT_PAREN")); break;
	case '\'': {
		string str = "";
		while(stream.advance() != '\''){
			if(stream.getChar() == '\n')
				throw lineCounter;
			str += stream.getChar();
		} tokens.push_back(Token(str, lineCounter, "STRING"));
	} break;
	case ':': {
		if(stream.peek() == '-'){
			tokens.push_back(Token(":-", lineCounter, "COLON_DASH"));
			stream.advance();
		}
		else
			tokens.push_back(Token(":", lineCounter, "COLON"));
	} break;
	case '#': return LineStream(0, " \n"); break;
	default: throw lineCounter; break;
	}
	return stream;
}

vector<Token> Scanner::getTokens() {
	return tokens;
}

Scanner::~Scanner() {

}


