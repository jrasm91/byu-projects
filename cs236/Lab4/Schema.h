/*
 * Schema.h
 *
 *  Created on: Oct 18, 2012
 *      Author: JRASM91
 */

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
	vector<string> getAttributes();
	string toString();
};

#endif /* SCHEMA_H_ */
