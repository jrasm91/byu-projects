
#pragma once
#ifndef RELATION_H_
#define RELATION_H_

#include <vector>
#include <string>
#include <sstream>
#include <set>

#include "Schema.h"
#include "Tuple.h"



using namespace std;

class Relation {

private:


public:
	string name;
	Schema schema;
	vector<Tuple> tuples;

	Relation();
	Relation(string name);
	Relation(string name, Schema scheme);
	Relation(string name, Schema scheme, vector<Tuple> tuples);

	Relation rename(int index, string value);
	Relation select(int index,string value);
	Relation select(int index1, int index2);
	Relation project(vector<int> indexList);

	string getName();
	string print();
	Schema getSchema();
	int getSize();
	vector<Tuple> getTuples();
	void addParam(string param);
	void addTuple(Tuple t);
	string toString();
};

#endif /* RELATION_H_ */
