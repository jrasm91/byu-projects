
#include "DatalogProgram.h"

using namespace std;

DatalogProgram::DatalogProgram() {
	schemes = vector<Predicate>();
	facts = vector<Predicate>();
	rules = vector<Rule>();
	queries = vector<Predicate>();
	domain = set<string>();
}

void DatalogProgram::addScheme(Predicate newScheme){
	schemes.push_back(newScheme);
}

void DatalogProgram::addFact(Predicate newFact){
	facts.push_back(newFact);
	addDomain(newFact.getParameters());
}

void DatalogProgram::addRule(Rule newRule){
	rules.push_back(newRule);
	addDomain(newRule.getPredicate().getParameters());
	for(int i = 0; i < (int)newRule.getPredicateList().size(); i++)
		addDomain(newRule.getPredicateList()[i].getParameters());
}

void DatalogProgram::addQuery(Predicate newQuery){
	queries.push_back(newQuery);
	addDomain(newQuery.getParameters());
}

void DatalogProgram::addDomain(vector<Parameter> pList){
	for(int i = 0; i < (int)pList.size(); i++){
		if(pList[i].getType() == "STRING")
			domain.insert(pList[i].getValue());
	}

}

string DatalogProgram::toSchemes(){
	stringstream ss;
	ss << "Schemes(" << schemes.size() << "):";
	for(int i = 0; i < (int)schemes.size(); i++)
		ss << "\n  " << schemes[i].toString();
	return ss.str();
}

string DatalogProgram::toDomain(){
	stringstream ss;
	ss << "Domain(" << domain.size() << "):";
	set<string>::iterator myIterator;
	for(myIterator = domain.begin();
		myIterator != domain.end();
		myIterator++)
		ss << "\n  " << "\'" << (*myIterator) << "\'";
	return ss.str();
}

string DatalogProgram::toFacts(){
	stringstream ss;
	ss << "Facts(" << facts.size() << "):";
	for(int i = 0; i < (int)facts.size(); i++)
		ss << "\n  " << facts[i].toString() << ".";
	return ss.str();
}

string DatalogProgram::toQueries(){
	stringstream ss;
	ss << "Queries(" << queries.size() << "):";
	for(int i = 0; i < (int)queries.size(); i++)
		ss << "\n  " << queries[i].toString() << "?";
	return ss.str();
}

string DatalogProgram::toRules(){
	stringstream ss;
	ss << "Rules(" << rules.size() << "):";
	for(int i = 0; i < (int)rules.size(); i++)
		ss << "\n  " << rules[i].toString();
	return ss.str();
}

string DatalogProgram::toString(){
	stringstream ss;
	ss << "Success!";;
	ss << endl << toSchemes();
	ss << endl << toFacts();
	ss << endl << toRules() ;
	ss << endl << toQueries();
	ss << endl << toDomain();
	return ss.str();
}

DatalogProgram::~DatalogProgram() {

}

