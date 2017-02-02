
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

string Relation::getName(){
	return name;
}

Schema Relation::getSchema(){
	return schema;
}


int Relation::getSize(){
	return tuples.size();
}

void Relation::rename(int index, string value){
	schema.attributes[index] =  value;
}

Relation Relation::select(int index, string value){
	Relation r(name, schema);
	set<Tuple>::iterator it;
	for(it = tuples.begin(); it != tuples.end(); it++)
		if((*it)[index] == value)
			r.tuples.insert(*it);
	return r;
}

Relation Relation::select(int index1, int index2){
	Relation r(name, schema);
	set<Tuple>::iterator it;
	for(it = tuples.begin(); it != tuples.end(); it++)
		if((*it)[index1] == (*it)[index2])
			r.tuples.insert(*it);
	return r;
}

Relation Relation::project(vector<int> &indexList){
	Relation r = Relation(name);
	for(int i = 0; i < (int)indexList.size(); i++)
		r.schema.attributes.push_back(schema.attributes[indexList[i]]);
	if(indexList.size() != 0){
		set<Tuple>::iterator it;
		for(it = tuples.begin(); it != tuples.end(); it++){
			Tuple newTuple = Tuple();
			for(int j = 0; j < (int)indexList.size(); j++)
				newTuple.push_back((*it)[indexList[j]]);
			r.tuples.insert(newTuple);
		}
	}
	return r;

}

void Relation::Union(Relation &r){
	set<Tuple>::iterator it;
	for(it = r.tuples.begin(); it != r.tuples.end(); it++)
		tuples.insert(*it);
}

Relation Relation::join(Relation &r){
	Relation s("test", joinSchemes(schema, r.schema));
	int before = schema.attributes.size() + r.schema.attributes.size();
	int after = s.schema.attributes.size();
	bool fast = (before == after);
	set<Tuple>::iterator it1;
	for(it1 = tuples.begin(); it1 != tuples.end(); it1++){
		set<Tuple>::iterator it2;
		for(it2 = r.tuples.begin(); it2 != r.tuples.end(); it2++){
				if(fast)
					s.fastJoin((*it1), (*it2));
				else if(joinable(schema, r.schema, (*it1), (*it2)))
					s.joinTuples(schema, r.schema, (*it1), (*it2));
			}
	}
	return s;
}

void Relation::joinTuples(Schema &s1, Schema &s2, Tuple t1, Tuple t2){
	Tuple t3 = t1;
	for(int i = 0; i < (int)t2.size(); i++)
		if(!s1.contains(s2.attributes[i]))
			t3.push_back(t2[i]);
	tuples.insert(t3);
}

void Relation::fastJoin(Tuple t1, Tuple t2){
	Tuple t3 = t1;
	for(int i = 0; i < (int)t2.size(); i++)
		t3.push_back(t2[i]);
	tuples.insert(t3);
}

Schema Relation::joinSchemes(Schema s1, Schema s2){
	for(int i = 0; i < (int)s2.attributes.size(); i++)
		s1.addParam(s2.attributes[i]);
	return s1;
}

bool Relation::joinable(Schema &s1, Schema &s2, Tuple t1, Tuple t2){
	for(int i = 0; i < (int)s1.attributes.size(); i++){
		for(int j = 0; j < (int)s2.attributes.size(); j++)
			if((s1.attributes[i] == s2.attributes[j]) && (t1[i] != t2[j]))
				return false;
	}
	return true;
}
void Relation::addParam(string param){
	schema.addParam(param);
}

int Relation::getTuples(){
	return tuples.size();
}

string Relation::print(){

	set<Tuple>::iterator i;
	stringstream ss;
	for(i = tuples.begin(); i != tuples.end(); i++){
		ss << "  ";
		for(int j = 0; j < (int)schema.attributes.size(); j++){
			if(j != 0)
				ss << ", ";
			ss << schema.attributes[j] << "=\'" << (*i)[j] << "\'";
		}
		ss << endl;
	}
	return ss.str();
}


