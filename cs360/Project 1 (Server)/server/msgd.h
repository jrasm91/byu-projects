#include <arpa/inet.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <vector>
#include <map>

#include <string>

using namespace std;

class Server {
public:
    Server(int);
    ~Server();

private:

    void create();
    void serve();
    void handle(int);

    string get_header(int);
    string get_request_length(int, int, string);
    string get_body(int, string);

    string parse_request(string);

    bool match(string, string);
    void store_message(string);
    string parse_name(string);
    string get_message(string);
    bool send_response(int, string);

    string list_messages(string);

    int port_;
    int server_;
    int buflen_;
    char* buf_;

    map<string, vector<string> > messageMap;
};
