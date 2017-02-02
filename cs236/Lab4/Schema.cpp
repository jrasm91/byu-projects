
#include "Schema.h"


Schema::Schema() {

}

Schema::Schema(vector<string> params) {
	attributes = params;
}

void Schema::addParam(string n){
	attributes.push_back(n);
}

vector<string> Schema::getAttributes(){
	return attributes;
}

string Schema::toString(){
	stringstream ss;
	for(int i = 0; i < (int)attributes.size(); i++){
		if(i > 0)
			ss << ", ";
		ss << attributes[i];
	}
	return ss.str();
}

