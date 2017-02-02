
#include "Rule.h"
#include <sstream>

Rule::Rule() {
	predicate = Predicate();
	predicateList = vector<Predicate>();
}

Rule::Rule(Predicate pred){
	predicate = pred;
	predicateList = vector<Predicate>();
}

Rule::Rule(Predicate pred, vector<Predicate> predList) {
	predicate = pred;
	predicateList = predList;
}

void Rule::addPredicate(Predicate pred){
	predicateList.push_back(pred);
}
Predicate Rule::getPredicate(){
	return predicate;
}
vector<Predicate> Rule::getPredicateList(){
	return predicateList;
}

string Rule::toString(){
	stringstream ss;
	ss << predicate.toString() << " :- ";
	for(int i = 0; i < (int)predicateList.size(); i++){
		if(i == 0)
			ss << predicateList[i].toString();
		else
			ss << "," + predicateList[i].toString();
	}
	ss << ".";
	return ss.str();
}

Rule::~Rule() {
}

