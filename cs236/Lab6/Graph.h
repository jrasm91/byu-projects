

#include <map>
#include <string>
#include <vector>
#include "Node.h"


#ifndef GRAPH_H_
#define GRAPH_H_

using namespace std;

class Graph {

private:
	vector<string> stack;
	int count;
	map<string, Node> nMap;
	void postorder();
public:
	Graph();
	int size();
	string printNode(string name);
	string printGraph();
	void analyzeGraph(string query);
	void reset();
	string printPostorderNumbers();
	string printRuleEvaluationOrder();
	string printBackwardEdges();
	void addNode(Node &node);
	virtual ~Graph();
};

#endif /* GRAPH_H_ */
