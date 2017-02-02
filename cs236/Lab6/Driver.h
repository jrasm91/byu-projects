

#ifndef DRIVER_H_
#define DRIVER_H_

#include "DatalogProgram.h"
#include "Graph.h"

using namespace std;

class Driver {

private:
	Graph graph;
	vector<string> ruleList;
	vector<string> queryList;
	string output;
	void analyzeQuery(Predicate query, int i);
	void runGraphOutput(DatalogProgram dlp);
	void runQueryOutput(DatalogProgram dlp);
	string getRuleName(int number);
	string getQueryName(int number);
	void initiateRules(vector<Rule> rules);
	void initiateQueries(vector<Predicate> queries);
public:
	Driver();
	string toString();
	void run(DatalogProgram dlp);
	virtual ~Driver();
};

#endif /* DRIVER_H_ */
