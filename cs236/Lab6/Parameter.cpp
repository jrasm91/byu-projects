
#include "Parameter.h"

Parameter::Parameter() {
	type = ""; // "ID" or "STRING"
	value = "";
}

Parameter::Parameter(string atype, string avalue) {
	type = atype;
	value = avalue;
}

string Parameter::getValue(){
	return value;
}

string Parameter::getType(){
	return type;
}

string Parameter::toString(){
	if(type == "ID")
		return value;
	else
		return ("'" + value + "'");
}

Parameter::~Parameter() {
}

