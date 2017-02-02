
#pragma once
#ifndef DATABASE_H_
#define DATABASE_H_

#include <string>
#include <vector>
#include <iostream>
#include <sstream>

#include "DatalogProgram.h"
#include "Relation.h"

#include <iostream>

using namespace std;

class Database {

private:
	string output;
	vector<Relation> relations;
	int getSize();
	void initSchemes(vector<Predicate> schemes);
	void initFacts(vector<Predicate> facts);
	void initRules(vector<Rule> rules);
	void initQueries(vector<Predicate> queries);
	void evaluateRule(Rule r);
	void performSelect(Predicate query, Relation &r);
	void performProject(Predicate query, Relation &r);
	void performProject2(Predicate p, Relation &r);
	Relation performJoin(vector<Relation> &relations);
	void performRename(Predicate query, Relation &r);
	Relation find(string name);
	vector<int> performSearch(Predicate &query);
public:
	Database();
	Database(DatalogProgram dlp);

	void addRelation(Relation &n);
	void addTuple(string name, Tuple &t);
	string toString();
};

#endif /* DATABASE_H_ */
