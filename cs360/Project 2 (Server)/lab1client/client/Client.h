#include <arpa/inet.h>
#include <errno.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>

#include <fstream>
#include <iostream>
#include <string>

using namespace std;

class Client {
public:
    Client(string, int, bool);
    ~Client();

private:

    void create();
    void listen();

    string readMessage();
    string parseCommand(string);
    string parseMessage(string);

    bool sendRequest(string);
    bool getResponse();
    bool match(string, string);

    int port_;
    string host_;
    int server_;
    int buflen_;
    char* buf_;
    bool debug_;
};
