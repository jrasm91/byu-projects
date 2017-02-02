
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
			myOutputFile << database.toString();
			cout << database.toString();

		} catch(string &e){
			cerr << "Failure!" << endl << "\t" << e << endl;
		}
		catch(int e){
			cerr << "Error on line: " << e;
		}
		myOutputFile.close();
	}
	else
		cerr << "Cannot open " + outputFile + " for writing" << endl;
}


