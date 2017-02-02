
#include "Parser.h"
#include <vector>
#include <iostream>

using namespace std;

Parser::Parser(){
	data = DatalogProgram();
	tokens = vector<Token>();
	index = 0;
	current = Token();
}

Parser::Parser(vector<Token> tokenList) {
	data = DatalogProgram();
	tokens = tokenList;
	index = 0;
	if(tokenList.size() == 0)
		current = Token();
	else
		current = tokens[index];
	p = Predicate();
	r = Rule();
	parseDatalogProgram();
}

Token Parser::nextToken(){
	if(index == (int)tokens.size()-1)
		return Token("End Of File", -1, "EOF");
	return tokens[++index];
}

void Parser::match(string type){
	if(type  == current.getType())
		current = nextToken();
	else
		throw string(current.toString());
}

void Parser::parseDatalogProgram(){
	match("SCHEMES");
	match("COLON");
	parseSchemeList();
	match("FACTS");
	match("COLON");
	parseFactList();
	match("RULES");
	match("COLON");
	parseRuleList();
	match("QUERIES");
	match("COLON");
	parseQueryList();
}
void Parser::parseSchemeList(){
	parseScheme();
	data.addScheme(p);
	if(current.getType() == "ID")
		parseSchemeList();
}
void Parser::parseScheme(){
	parsePredicate();
}
void Parser::parseFactList(){
	if(current.getType() == "ID"){
		parseFact();
		data.addFact(p);
		parseFactList();
	}
}
void Parser::parseFact(){
	parsePredicate();
	match("PERIOD");
}
void Parser::parseRuleList(){
	if(current.getType() == "ID"){
		parseRule();
		data.addRule(r);
		parseRuleList();
	}
}
void Parser::parseRule(){
	parsePredicate();
	r = Rule(p);
	match("COLON_DASH");
	parsePredicateList();
	match("PERIOD");
}
void Parser::parseQueryList(){
	parseQuery();
	data.addQuery(p);
	if(current.getType() == "ID")
		parseQueryList();

	else if(current.getType() != "EOF")
		throw string(current.toString());
}
void Parser::parseQuery(){
	parsePredicate();
	match("Q_MARK");
}
void Parser::parsePredicateList(){
	parsePredicate();
	r.addPredicate(p);
	if(current.getType() == "COMMA"){
		match("COMMA");
		parsePredicateList();
	}
}
void Parser::parsePredicate(){
	p = Predicate(current.getValue());
	match("ID");
	match("LEFT_PAREN");
	parseParameterList();
	match("RIGHT_PAREN");
}
void Parser::parseParameterList(){
	parseParameter();
	if(current.getType() == "COMMA"){
		match("COMMA");
		parseParameterList();
	}
}

void Parser::parseParameter(){
	if(current.getType() == "STRING" ||	current.getType() == "ID"){
		p.addParameter(Parameter(current.getType(), current.getValue()));
		match(current.getType());
	}
	else
		match("who knows what it is?");
}

DatalogProgram Parser::getData(){
	return data;
}

Parser::~Parser() {
}

