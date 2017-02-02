
#pragma once
#ifndef PARAMETER_H_
#define PARAMETER_H_
#include <string>

using namespace std;

class Parameter {
private:
	string type;
	string value;

public:
	Parameter();
	Parameter(string atype, string avalue);
	string getValue();
	string getType();
	string toString();
	virtual ~Parameter();
};

#endif /* PARAMETER_H_ */
