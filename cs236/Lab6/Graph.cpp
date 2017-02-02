

#include "Graph.h"

Graph::Graph() {
	count = 1;
}

void Graph::addNode(Node &node){
	nMap[node.name] = node;
}

int Graph::size(){
	return nMap.size();
}

string Graph::printGraph(){
	stringstream ss;
	map<string, Node>::iterator iter;
	for(iter = nMap.begin(); iter != nMap.end(); iter++)
		ss << "  " << (*iter).second.print() << endl;

	return ss.str();
}

void Graph::reset(){
	map<string, Node>::iterator iter;
	for(iter = nMap.begin(); iter != nMap.end(); iter++){
		(*iter).second.reset();
	}
}

void Graph::analyzeGraph(string query){
	stack.clear();
	nMap[query].marked = true;
	stack.push_back(query);
	count = 1;
	postorder();

}

void Graph::postorder(){
	while(stack.size() != 0){
		string name = stack[stack.size() - 1];
		set<string>::iterator iter;
		for(iter = nMap[name].nList.begin(); iter != nMap[name].nList.end(); iter++){
			if(!nMap[(*iter)].marked){
				nMap[(*iter)].marked = true;
				stack.push_back(*iter);
				postorder();
			}
		}
		if(stack.size() != 0){
			nMap[stack[stack.size() -1]].postNum = count++;
			stack.pop_back();
		}
	}
}
string Graph::printPostorderNumbers(){

	stringstream ss;
	map<string, Node>::iterator iter;
	for(iter = nMap.begin(); iter != nMap.end(); iter++)
		if((*iter).second.postNum != -1)
			ss << "    " << (*iter).second.name << ": " << (*iter).second.postNum << endl;
	return ss.str();
}

string Graph::printRuleEvaluationOrder(){
	map<int, string> map2;
	stringstream ss;
	map<string, Node>::iterator iter;
	iter = nMap.begin();
	while((iter != nMap.end()) && ((*iter).second.name != "R1"))
		iter++;
	for(; iter != nMap.end(); iter++){
		if((*iter).second.postNum != -1)
			map2[(*iter).second.postNum] = (*iter).first;
	}
	map<int, string>::iterator iter2;
	for(iter2 = map2.begin(); iter2 != map2.end(); iter2++)
		ss << "    " << (*iter2).second << endl;
	return ss.str();
}

string Graph::printBackwardEdges(){
	stringstream ss;
	map<string, Node>::iterator iter;
	set<string>::iterator iter2;
	for(iter = nMap.begin(); iter != nMap.end(); iter++){
		Node node1 = (*iter).second;
		if(node1.postNum != -1){
			bool first = true;
			for(iter2 = node1.nList.begin(); iter2 != node1.nList.end(); iter2++){
				Node node2 = nMap[(*iter2)];
				if(node2.postNum >= node1.postNum){
					if(first){
						first = false;
						ss << "    " << node1.name << ":";
					}
					ss << " " << node2.name;
				}
			}
			if(first == false)
				ss << endl;
		}
	}
	return ss.str();
}

Graph::~Graph() {
}

