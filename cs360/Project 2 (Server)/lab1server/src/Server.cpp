
#include "Server.h"
#include "ClientHandler.h"

int NUM_WORKERS = 10;

int main(int argc, char **argv) {
	int option, port;

	port = 3000;
	while ((option = getopt(argc,argv,"p:")) != -1) {
		switch (option) {
		case 'p':
			port = atoi(optarg);
			break;
		default:
			cout << "server [-p port]" << endl;
			exit(EXIT_FAILURE);
		}
	}
	printf("Starting Server(port='%d')\n", port);
	Server server = Server(port);
}

Server::Server(int port) {
	port_ = port;
	sharedData = new SharedData;
	sharedData->clientBuffer = ClientBuffer();
	sharedData->messageMap = MessageDatabase();
	sem_init(&sharedData->SEM_READ, 0, 1);
	initializeSocket();
	makeWorkerThreads();
	acceptClients();
}

Server::~Server(void) {
	for (int i=0; i<NUM_WORKERS; i++) {
		pthread_join(*threads[i], NULL);
		delete threads[i];
	}
}

void Server::initializeSocket(void) {
	struct sockaddr_in server_addr;

	// setup socket address structure
	memset(&server_addr,0,sizeof(server_addr));
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(port_);
	server_addr.sin_addr.s_addr = INADDR_ANY;

	// create socket
	server_ = socket(PF_INET,SOCK_STREAM,0);
	if (!server_) {
		perror("socket");
		exit(-1);
	}

	// set socket to immediately reuse port when the application closes
	int reuse = 1;
	if (setsockopt(server_, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
		perror("setsockopt");
		exit(-1);
	}

	// call bind to associate the socket with our local address and port
	if (bind(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
		perror("bind");
		exit(-1);
	}

	// convert the socket to listen for incoming connections
	if (listen(server_,SOMAXCONN) < 0) {
		perror("listen");
		exit(-1);
	}
}

void Server::makeWorkerThreads(void){
	printf("Making Worker Threads: [%d]\n", NUM_WORKERS);
	for (int i=0; i<NUM_WORKERS; i++) {
		pthread_t* thread = new pthread_t;
		pthread_create(thread, NULL, &Server::makeClientHandler, (void *) sharedData);
		threads.push_back(thread);
	}
}

void* Server::makeClientHandler(void* ptr) {
	SharedData* data;
	data = (SharedData*)ptr;
	data->threadId += 1;
	ClientHandler((SharedData*)ptr);
	pthread_exit(0);
}

void Server::acceptClients(void){
	int client;
	struct sockaddr_in client_addr;
	socklen_t clientlen = sizeof(client_addr);
	while(true){
		while ((client = accept(server_,(struct sockaddr *)&client_addr,&clientlen)) > 0) {
			sharedData->clientBuffer.enqueue(client);
		}
	}
	close(server_);
}


