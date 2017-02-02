


#include "Driver.h"

Driver::Driver() {

}

void Driver::run(DatalogProgram dlp){
	runGraphOutput(dlp);
	runQueryOutput(dlp);
}

void Driver::runGraphOutput(DatalogProgram dlp){
	initiateRules(dlp.getRules());
	initiateQueries(dlp.getQueries());
	stringstream ss;
	ss << "Dependency Graph" << endl;
	ss << graph.printGraph() << endl;
	output = ss.str();
}

void Driver::runQueryOutput(DatalogProgram dlp){
	vector<Predicate> queries = dlp.getQueries();
	for(int i = 0; i < (int)queries.size(); i++)
		analyzeQuery(queries[i], i);

}

void Driver::initiateRules(vector<Rule> rules){

	for(int i = 0; i < (int)rules.size(); i++){
		ruleList.push_back(rules[i].getPredicate().getID());
		Node n = Node(getRuleName(i + 1));
		vector<Predicate> pList = rules[i].getPredicateList();
		for(int j = 0; j < (int)pList.size(); j++)
			for(int k = 0; k < (int)rules.size(); k++)
				if(pList[j].getID() == rules[k].getPredicate().getID())
					n.addConnection(getRuleName(k + 1));
		graph.addNode(n);
	}
}

void Driver::initiateQueries(vector<Predicate> queries){

	for(int i = 0; i < (int)queries.size(); i++){
		queryList.push_back(queries[i].getID());
		Node n = Node(getQueryName(i + 1));
		for(int j = 0; j < (int)ruleList.size(); j++)
			if(ruleList[j] == queries[i].getID())
				n.addConnection(getRuleName(j + 1));
		graph.addNode(n);
	}
}

void Driver::analyzeQuery(Predicate query, int i){
	graph.reset();
	graph.analyzeGraph(getQueryName(i + 1));
	stringstream ss;
	ss << output;
	ss << query.toString() << "?" << endl << endl;
	ss << "  Postorder Numbers" << endl;
	ss << graph.printPostorderNumbers() << endl;
	ss << "  Rule Evaluation Order" << endl;
	ss << graph.printRuleEvaluationOrder() << endl;
	ss << "  Backward Edges" << endl;
	ss << graph.printBackwardEdges() << endl;
	output = ss.str();
}

string Driver::getRuleName(int number){
	stringstream ss;
	ss << "R" << number;
	return ss.str();
}

string Driver::getQueryName(int number){
	stringstream ss;
	ss << "Q" << number;
	return ss.str();
}

string Driver::toString(){
	return output;
}


Driver::~Driver() {
	// TODO Auto-generated destructor stub
}

