
import argparse
import select
import socket
import sys

from clientHandler import ClientHandler

DEFAULT_PORT = 8000
CONFIG_FILE = 'web.conf'

class Server():
    def __init__(self):
        self.parseArguments()
        self.configure()
        self.openSocket()
        self.clients = {}
        self.start()

    def parseArguments(self):
        parser = argparse.ArgumentParser(prog='Web Server', description='A simple web-server', add_help=True)
        parser.add_argument('-p', '--port', type=int, action='store', help='Port Number',default=DEFAULT_PORT)
        parser.add_argument('-d', '--debug', action='store_true', help='Flag for debug', default=False)
        args = parser.parse_args()
        self.port = args.port
        self.debug = args.debug
        self.host = ""
        
    def configure(self):
        with open(CONFIG_FILE, 'rb') as file:
            
            for line in file:
                if line[0] == '#':
                    continue
                
                print line
                continue
                type, value,  = line.split[0]
                if type == 'hostname':
                    pass
                file.close()

    def openSocket(self):
        """ Setup the socket for incoming clients """
        try:
            self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR,1)
            self.server.bind((self.host,self.port))
            self.server.listen(5)
        except socket.error, (value,message):
            if self.server:
                self.server.close()
            print "Could not open socket: %s (errno %s)" % (message, value)
            sys.exit(1)

    def start(self):
        """ Use poll() to handle each incoming client."""
        print "Started Server(port=%s, debug=%s)" % (self.port, self.debug)
        self.poller = select.epoll()
        self.pollmask = select.EPOLLIN | select.EPOLLHUP | select.EPOLLERR
        self.poller.register(self.server,self.pollmask)
        while True:
            # poll sockets
            try:
                fds = self.poller.poll(timeout=1)
            except:
                return
            for (fd,event) in fds:
                # handle errors
                if event & (select.POLLHUP | select.POLLERR):
                    self.handleError(fd)
                    continue
                # handle the server socket
                if fd == self.server.fileno():
                    self.handleServer()
                    continue
                # handle client socket
                self.handleClient(fd)

    def handleError(self,fd):
        self.poller.unregister(fd)
        if fd == self.server.fileno():
            # recreate server socket
            self.server.close()
            self.openSocket()
            self.poller.register(self.server,self.pollmask)
        else:
            # close the socket
            self.clients[fd].close()
            del self.clients[fd]

    def handleServer(self):
        (client,address) = self.server.accept()
        self.clients[client.fileno()] = client
        self.poller.register(client.fileno(),self.pollmask)

    def handleClient(self,fd):
        print "Handling Client..."
        ch = ClientHandler(self.clients[fd])
        if not ch.handle():
            self.poller.unregister(fd)
            self.clients[fd].close()
            del self.clients[fd]

if __name__ == '__main__':
    Server()
    
    
    
    
    
    