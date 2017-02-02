
#pragma once
#ifndef RELATION_H_
#define RELATION_H_

#include <vector>
#include <string>
#include <sstream>
#include <set>
#include <iostream>
#include <iterator>

#include "Schema.h"
#include "Tuple.h"



using namespace std;

class Relation {

private:

	bool joinable(Schema &s1, Schema &s2, Tuple t1, Tuple t2);
	void joinTuples(Schema &s1, Schema &s2, Tuple t1, Tuple t2);
	void fastJoin(Tuple t1, Tuple t2);
	Schema joinSchemes(Schema s1, Schema s2);

public:
	string name;
	Schema schema;
	set<Tuple> tuples;

	Relation();
	Relation(string name);
	Relation(string name, Schema scheme);

	void rename(int index, string value);
	Relation select(int index,string value);
	Relation select(int index1, int index2);
	Relation project(vector<int> &indexList);
	Relation join(Relation &r);
	void Union(Relation &r);

	string getName();
	string print();
	int getSize();
	Schema getSchema();
	int getTuples();
	void addParam(string param);
	void addTuple(Tuple &t);
};

#endif /* RELATION_H_ */
