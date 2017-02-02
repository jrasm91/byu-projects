
#pragma once
#ifndef PARSER_H_
#define PARSER_H_
#include <vector>
#include "Token.h"
#include "Rule.h"
#include "DatalogProgram.h"

using namespace std;

class Parser {
private:
	int index;
	Token current;
	vector<Token> tokens;
	DatalogProgram data;
	Predicate p;
	Rule r;
	Token nextToken();
	void match(string token);
	void parseDatalogProgram();
	void parseSchemeList();
	void parseScheme();
	void parseFactList();
	void parseFact();
	void parseRuleList();
	void parseRule();
	void parseQueryList();
	void parseQuery();
	void parsePredicateList();
	void parsePredicate();
	void parseParameterList();
	void parseParameter();

public:
	Parser();
	Parser(vector<Token> tokenList);
	DatalogProgram getData();
	virtual ~Parser();
};

#endif /* PARSER_H_ */
