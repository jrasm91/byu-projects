

#pragma once
#ifndef DATALOGPROGRAM_H_
#define DATALOGPROGRAM_H_

#include <sstream>
#include <vector>
#include <set>
#include <string>
#include "Predicate.h"
#include "Rule.h"


using namespace std;

class DatalogProgram {
private:
	vector<Predicate> schemes;
	vector<Predicate> facts;
	vector<Rule> rules;
	vector<Predicate> queries;
	set<string> domain;
	string toSchemes();
	string toFacts();
	string toQueries();
	string toRules();
	string toDomain();
	void addDomain(vector<Parameter> pList);

public:
	DatalogProgram();
	void addScheme(Predicate newScheme);
	void addFact(Predicate newFact);
	void addQuery(Predicate newQuery);
	void addRule(Rule newRule);
	string toString();
	virtual ~DatalogProgram();
};

#endif /* DATALOGPROGRAM_H_ */
