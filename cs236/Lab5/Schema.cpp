
#include "Schema.h"


Schema::Schema() {

}

Schema::Schema(vector<string> params) {
	attributes = params;
}

void Schema::addParam(string n){
	for(int i = 0; i < (int)attributes.size(); i++)
		if(attributes[i] == n)
			return;
	attributes.push_back(n);
}

bool Schema::contains(string a){
	for(int i = 0; i < (int)attributes.size(); i++)
		if(attributes[i] == a)
			return true;
	return false;
}

int Schema::indexOf(string s){
	for(int i = 0; i < (int)attributes.size(); i++)
		if(attributes[i] == s)
			return i;
	return -1;
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

