
#pragma once
#ifndef PREDICATE_H_
#define PREDICATE_H_
#include <vector>
#include <string>
#include "Parameter.h"

using namespace std;

class Predicate {

private:
	string identifier;
	vector<Parameter> parameters;

public:
	Predicate();
	Predicate(string ident);
	Predicate(string ident, vector<Parameter> params);
	string getID();
	vector<Parameter> getParameters();
	void addParameter(Parameter param);
	string toString();
	virtual ~Predicate();
};

#endif /* PREDICATE_H_ */
