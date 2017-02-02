

#ifndef NODE_H_
#define NODE_H_

#include <string>
#include <set>
#include <sstream>

using namespace std;

class Node {

private:
	string name;
	int postNum;
	bool marked;
	set<string> nList;
	friend class Graph;

public:
	Node();
	Node(string name);
	void reset();
	void addConnection(string name);
	string print();
	virtual ~Node();
};

#endif /* NODE_H_ */
