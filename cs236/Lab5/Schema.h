
#pragma once
#ifndef SCHEMA_H_
#define SCHEMA_H_

#include <vector>
#include <string>
#include <sstream>

using namespace std;

class Schema {

private:
	vector<string> attributes;

public:
	Schema();
	Schema(vector<string> params);
	void addParam(string n);
	bool contains(string a);
	string toString();
	int indexOf(string s);
	friend class Relation;
	friend class Database;
};

#endif /* SCHEMA_H_ */
