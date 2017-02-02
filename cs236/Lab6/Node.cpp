
#include "Node.h"

Node::Node() {
	marked = false;
	postNum = -1;

}

Node::Node(string aname) {
	name = aname;
	marked = false;
	postNum = -1;

}

void Node::reset(){
	marked = false;
	postNum = -1;
}

void Node::addConnection(string name){
	nList.insert(name);
}



string Node::print(){
	stringstream ss;
	ss << name << ":";
	set<string>::iterator iter;
	for(iter = nList.begin(); iter != nList.end(); iter++)
		ss << " " << (*iter);
	return ss.str();
}

Node::~Node() {
	// TODO Auto-generated destructor stub
}

