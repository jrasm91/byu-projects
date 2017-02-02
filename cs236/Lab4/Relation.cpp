
#include "Relation.h"

Relation::Relation() {
	name = "";
}

Relation::Relation(string aname) {
	name = aname;
}

Relation::Relation(string aname, Schema aschema){
	name = aname;
	schema = aschema;
}

Relation::Relation(string aname, Schema aschema, vector<Tuple> atuples){
	name = aname;
	schema = aschema;
	tuples = atuples;
}

string Relation::getName(){
	return name;
}

Schema Relation::getSchema(){
	return schema;
}

vector<Tuple> Relation::getTuples(){
	return tuples;
}

int Relation::getSize(){
	return tuples.size();
}


Relation Relation::rename(int index, string value){
	vector<string> v = schema.getAttributes();
	v[index] = value;
	Schema s = Schema(v);
	Relation r = Relation(name, s, tuples);
	return r;
}

Relation Relation::select(int index, string value){
	Relation r(name, schema);
	for(int i = 0; i < (int)tuples.size(); i++)
		if(tuples[i][index] == value)
			r.addTuple(tuples[i]);
	return r;
}

Relation Relation::select(int index1, int index2){
	Relation r(name, schema);
	for(int i = 0; i < (int)tuples.size(); i++){
		string value1 = tuples[i][index1];
		string value2 = tuples[i][index2];
		if(value1 == value2)
			r.addTuple(tuples[i]);
	}
	return r;
}

Relation Relation::project(vector<int> indexList){
	Relation r = Relation(name);
	for(int i = 0; i < (int)indexList.size(); i++)
		r.addParam(schema.getAttributes()[indexList[i]]);
	for(int i = 0; i < (int)tuples.size(); i++){
		Tuple newTuple = Tuple();
		for(int j = 0; j < (int)indexList.size(); j++)
			newTuple.push_back(tuples[i][indexList[j]]);
		if(indexList.size() != 0)
			r.addTuple(newTuple);
	}
	return r;
}

void Relation::addParam(string param){
	schema.addParam(param);
}

void Relation::addTuple(Tuple t){
	for(int i = 0; i < (int)tuples.size(); i++)
		if(tuples[i] == t)
			return;
	tuples.push_back(t);
}

string Relation::print(){

	set<Tuple> s;
	for(int i = 0; i < (int)tuples.size(); i++)
		s.insert(tuples[i]);
	set<Tuple>::iterator i;
	stringstream ss;
	for(i = s.begin(); i != s.end(); i++){
		ss << "  ";
		for(int j = 0; j < (int)schema.getAttributes().size(); j++){
			if(j != 0)
				ss << ", ";
			ss << schema.getAttributes()[j] << "=\'" << (*i)[j] << "\'";
		}
		ss << endl;
	}
	return ss.str();
}


