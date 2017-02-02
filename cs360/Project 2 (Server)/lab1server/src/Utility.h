#ifndef UTILITY_H_
#define UTILITY_H_

#include "Message.h"
#include <vector>
#include <map>


using namespace std;

class Utility{
public:
	static string getNthSplit(int n, string input);
	static bool match(string req, string exp);
};


#endif /* UTILITY_H_ */
