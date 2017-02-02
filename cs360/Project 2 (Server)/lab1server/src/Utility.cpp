

#include "Utility.h"

#include <iostream>
#include <string>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <vector>

#include <regex.h>
#include <stdio.h>

//#include <boost/algorithm/string.hpp>



string Utility::getNthSplit(int n, string input){
	string buf;
	stringstream ss(input);
	vector<string> tokens;
	while ((ss >> buf))
		tokens.push_back(buf);
	return tokens[n];
}

bool Utility::match(string req, string exp){
	regex_t regex;
	regcomp(&regex, exp.c_str(), 0);
	return !regexec(&regex, req.c_str(), 0, NULL, 0);
}



