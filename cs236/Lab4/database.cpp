
#include "Database.h"


Database::Database() {
}

Database::Database(DatalogProgram dlp) {
	initSchemes(dlp.getSchemes());
	initFacts(dlp.getFacts());
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

 void Database::initQueries(vector<Predicate> queries){

	 stringstream ss;
	for(int i = 0; i < (int)queries.size(); i++){
		Relation r = find(queries[i].getID());;
		r = performSelect(queries[i], r);
		if(r.getTuples().size() != 0)
			ss << queries[i].toString() << "? Yes(" << r.getTuples().size() << ")";
		else
			ss << queries[i].toString() << "? No";
		r = performProject(queries[i], r);
		r = performRename(queries[i], r);
		ss << endl << r.print();
	}
	output = ss.str();
}

Relation Database::performSelect(Predicate query, Relation r){
	vector<Parameter> params = query.getParameters();
	for(int j = 0; j < (int)params.size(); j++){
		if(params[j].getType() == "ID")
			for(int k = j + 1; k < (int)params.size(); k++)
				if(params[j].getValue() == params[k].getValue())
					r = r.select(j, k);
		if(params[j].getType() == "STRING")
			r = r.select(j, params[j].getValue());
	}
	return r;
}

Relation Database::performProject(Predicate query, Relation r){
	r = r.project(performSearch(query, r));
	return r;
}

Relation Database::performRename(Predicate query, Relation r){
	vector<int> indexes = performSearch(query, r);
	for(int i = 0; i < (int)indexes.size(); i++)
		r = r.rename(i, query.getParameters()[indexes[i]].getValue());

	return r;
}

vector<int> Database::performSearch(Predicate query, Relation r){

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



Relation Database::find(string name){
	for(int i = 0; i < (int)relations.size(); i++)
		if(relations[i].getName() == name)
			return relations[i];
	return Relation("-1");
}
void Database::addRelation(Relation n){
	relations.push_back(n);
}

void Database::addTuple(string name, Tuple t){
	for(int i = 0; i < (int)relations.size(); i++)
		if(name == relations[i].getName())
			relations[i].addTuple(t);
}

string Database::toString(){
	return output;
}


