
#pragma once
#ifndef DATABASE_H_
#define DATABASE_H_

#include <string>
#include <vector>
#include <iostream>
#include <sstream>

#include "DatalogProgram.h"
#include "Relation.h"

using namespace std;

class Database {

private:
	string output;
	vector<Relation> relations;
	void initSchemes(vector<Predicate> schemes);
	void initFacts(vector<Predicate> facts);
	void initQueries(vector<Predicate> queries);
	vector<int> performSearch(Predicate query, Relation r);
	Relation performSelect(Predicate query, Relation r);
	Relation performProject(Predicate query, Relation r);
	Relation performRename(Predicate query, Relation r);
	Relation find(string name);
public:
	Database();
	Database(DatalogProgram dlp);
	void addRelation(Relation n);
	void addTuple(string name, Tuple t);
	string toString();
};

#endif /* DATABASE_H_ */
