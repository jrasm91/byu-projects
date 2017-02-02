
#include "Database.h"


Database::Database() {
}

Database::Database(DatalogProgram dlp) {
	initSchemes(dlp.getSchemes());
	initFacts(dlp.getFacts());
	initRules(dlp.getRules());
	initQueries(dlp.getQueries());


}

void Database::initSchemes(vector<Predicate> schemes){
	for(int i = 0; i < (int)schemes.size(); i++){
		Predicate r = schemes[i];
		Relation r1(r.getID());
		for(int j = 0; j < (int)r.getParameters().size(); j++)
			r1.addParam(r.getParameters()[j].getValue());
		relations.push_back(r1);
	}
}

void Database::initFacts(vector<Predicate> facts){
	for(int i = 0; i < (int)facts.size(); i++){
		Predicate r = facts[i];
		string name = r.getID();
		Tuple t;
		for(int j = 0; j < (int)r.getParameters().size(); j++)
			t.push_back(r.getParameters()[j].getValue());
		addTuple(name, t);
	}
}

void Database::initRules(vector<Rule> rules){
	bool change = true;
	int i = 0;
	while(change){
		i++;
		change = false;
		int before = getSize();
		for(int j = 0; j < (int)rules.size(); j++)
			evaluateRule(rules[j]);
		if(before != getSize())
			change = true;
	}
	stringstream ss;
	ss << "Schemes populated after " << i  << " passes through the Rules." << endl;
	output = ss.str();
}

void Database::evaluateRule(Rule rule){
	Predicate query1 = rule.getPredicate();
	vector<Predicate> queries = rule.getPredicateList();
	vector<Relation> toJoin;
	for(int i = 0; i < (int)queries.size(); i++){
		Relation r = find(queries[i].getID());
		performSelect(queries[i], r);
		performProject(queries[i], r);
		performRename(queries[i], r);
		toJoin.push_back(r);
	}
	Relation r2 = performJoin(toJoin);
	performProject2(query1, r2);
	for(int i = 0; i < (int)relations.size(); i++)
		if(relations[i].getName() == query1.getID()){
			relations[i].Union(r2);
			break;
		}
}

void Database::initQueries(vector<Predicate> queries){
	stringstream ss;
	for(int i = 0; i < (int)queries.size(); i++){
		Relation r = find(queries[i].getID());
		performSelect(queries[i], r);
		if(r.getTuples() != 0)
			ss << queries[i].toString() << "? Yes(" << r.getTuples() << ")";
		else
			ss << queries[i].toString() << "? No";
		performProject(queries[i], r);
		performRename(queries[i], r);
		ss << endl << r.print();
	}
	ss << "Done!";
	output += ss.str();
}

void Database::performSelect(Predicate query, Relation &r){
	vector<Parameter> params = query.getParameters();
	for(int j = 0; j < (int)params.size(); j++){
		if(params[j].getType() == "ID")
			for(int k = j + 1; k < (int)params.size(); k++)
				if(params[j].getValue() == params[k].getValue())
					r = r.select(j, k);
		if(params[j].getType() == "STRING")
			r = r.select(j, params[j].getValue());
	}
}

void Database::performProject(Predicate query, Relation &r){
	vector<int> indexes = performSearch(query);
	r = r.project(indexes);
}

void Database::performProject2(Predicate p, Relation &r){
	Schema s;
	vector<Parameter> params = p.getParameters();
	for(int i = 0; i < (int)params.size(); i++)
		s.addParam(params[i].getValue());
	vector<int> indexes;
	for(int i= 0; i < (int)s.attributes.size(); i++)
		indexes.push_back(r.schema.indexOf(s.attributes[i]));
	r = r.project(indexes);
}

void Database::performRename(Predicate query, Relation &r){
	vector<int> indexes = performSearch(query);
	for(int i = 0; i < (int)indexes.size(); i++)
		r.rename(i, query.getParameters()[indexes[i]].getValue());
}

vector<int> Database::performSearch(Predicate &query){
	vector<int> indexes = vector<int>();
	vector<Parameter> params = query.getParameters();
	for(int j = 0; j < (int)params.size(); j++){
		bool next = true;
		if(params[j].getType() == "ID"){
			next = false;
			for(int k = 0; k < j; k++)
				if(params[j].getValue() == params[k].getValue())
					next = true;
		}
		if(!next)
			indexes.push_back(j);
	}
	return indexes;
}

Relation Database::performJoin(vector<Relation> &relations){
	Relation r = relations[0];
	for(int i = 1; i < (int)relations.size(); i++)
		r = relations[i].join(r);
	return r;
}


Relation Database::find(string name){
	for(int i = 0; i < (int)relations.size(); i++)
		if(relations[i].getName() == name)
			return relations[i];
	return Relation("-1");
}

int Database::getSize(){
	int total = 0;
	for(int i = 0; i < (int)relations.size(); i++)
		total += relations[i].getSize();
	return total;
}

void Database::addRelation(Relation &n){
	relations.push_back(n);
}

void Database::addTuple(string name, Tuple &t){
	for(int i = 0; i < (int)relations.size(); i++)
		if(name == relations[i].getName())
			relations[i].tuples.insert(t);
}

string Database::toString(){
	return output;
}


