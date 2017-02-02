
#pragma once
#ifndef RULE_H_
#define RULE_H_
#include "Predicate.h"

using namespace std;

class Rule {
private:
	Predicate predicate;
	vector<Predicate> predicateList;

public:
	Rule();
	Rule(Predicate pred);
	Rule(Predicate pred, vector<Predicate> predList);
	void addPredicate(Predicate pred);
	vector<Predicate> getPredicateList();
	Predicate getPredicate();
	string toString();
	virtual ~Rule();
};

#endif /* RULE_H_ */
