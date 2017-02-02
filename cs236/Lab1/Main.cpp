

#include <iostream>
#include  "Token.h"
#include "LexicalAnalyzer.h"
#include <vector>
#include <fstream>

using namespace std;


int main(int argc, char* argv[]) {

	string inputFile = argv[1];
	string outputFile = argv[2];
	ofstream myOutputFile;
	myOutputFile.open(outputFile.data());

	if(myOutputFile){
		try{
			LexicalAnalyzer la(inputFile.data());
			vector<Token> tokenVector = la.getTokens();
			for(int i = 0; i < (int)tokenVector.size(); i++)
				myOutputFile << tokenVector[i].toString();
			myOutputFile << "Total Tokens = " << tokenVector.size();
			cout << "Total Tokens = " << tokenVector.size();

		} catch(int e){
			myOutputFile.clear();
			myOutputFile << "Error on line " << e << endl;
		}
		myOutputFile.close();
	}
	else
		cout << "Cannot open " + outputFile + "for reading" << endl;

}


