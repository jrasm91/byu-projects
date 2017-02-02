
#include <iostream>
#include <vector>
#include <fstream>
#include <string>

#include "Database.h"
#include "DatalogProgram.h"
#include "Parser.h"
#include "Scanner.h"
#include "Token.h"

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
			Database database(dlp);
			cout << database.toString();
			myOutputFile << database.toString();

		} catch(string &e){
			myOutputFile << "Failure!" << endl << "\t" << e << endl;
		}
		catch(int e){
			myOutputFile << "Error on line: " << e;
		}
		myOutputFile.close();
	}
	else
		cerr << "Cannot open " + outputFile + " for writing" << endl;
}


