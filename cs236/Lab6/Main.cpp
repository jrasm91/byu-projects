
#include <iostream>
#include <vector>
#include <fstream>
#include  "Token.h"
#include "Scanner.h"
#include "Parser.h"
#include "DatalogProgram.h"
#include "Driver.h"
#include "Parameter.h"

using namespace std;

int main(int argc, char* argv[]) {
	string inputFile = argv[1];
	string outputFile = argv[2];
	ofstream myOutputFile;
	myOutputFile.open(outputFile.data());
	if(myOutputFile){
		try{
			Scanner scanner = Scanner(inputFile.data());
			vector<Token> tokenList = scanner.getTokens();
			Parser parser(tokenList);
			DatalogProgram dlp = parser.getData();
			Driver driver = Driver();
			driver.run(dlp);
			myOutputFile << driver.toString();
		}
		catch(string &e){	myOutputFile << "Failure!" << endl << "\t" << e << endl; }
		catch(int &e){ myOutputFile << "Error on line: " << e; }
	}
	else
		myOutputFile << "Cannot open " + outputFile + " for writing" << endl;
	myOutputFile.close();
}


