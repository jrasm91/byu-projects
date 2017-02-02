
#include "Predicate.h"
#include <sstream>

using namespace std;

Predicate::Predicate() {
	identifier = "";
	parameters = vector<Parameter>();
}

Predicate::Predicate(string ident){
	identifier = ident;
	parameters = vector<Parameter>();
}

Predicate::Predicate(string ident, vector<Parameter> params) {
	identifier = ident;
	parameters = params;
}

string Predicate::getID(){
	return identifier;
}

vector<Parameter> Predicate::getParameters(){
	return parameters;
}

void Predicate::addParameter(Parameter param){
	parameters.push_back(param);
}

string Predicate::toString(){
	stringstream ss;
	ss << identifier << "(";
	for(int i = 0; i < (int)parameters.size(); i++){
		if(i == 0)
			ss << parameters[i].toString();
		else
			ss << "," << parameters[i].toString();
	}
	ss << ")";
	return ss.str();
}

Predicate::~Predicate() {
}

