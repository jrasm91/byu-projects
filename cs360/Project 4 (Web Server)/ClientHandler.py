

from sendfile import sendfile

import re
import os

class ClientHandler(object):
    def __init__(self, client):
        self.size = 1024
        self.client = client
    
    def handle(self):
        data, headers = self.recv()
        
        if not data:
            return False
        
        print "Headers:"
        for key in headers:
            print "dict[%s] --> %s" % (key, headers[key])
       
        fileName = 'index.html'
        serverName = 'localhost'
        contentType = 'text/html'
        statusCode = 200
        statusMessage = 'OK'
        
        headers = []
        headers.append("HTTP/1.1 %s %s" % (statusCode, statusMessage))
        headers.append("Server: %s" % (serverName))
        headers.append("Content-Type: %s" % contentType)
        headers.append("Content-Length: " + str(os.path.getsize(fileName)))
        with open(fileName, 'rb') as sendFile:
            self.send(headers, sendFile)
            sendFile.close()
        return True
    
    def recv(self):
        data = self.client.recv(self.size)
        headers = dict(re.findall(r"(?P<name>.*?): (?P<value>.*?)\r\n", data))
        return (data, headers)
    
    def send(self, headers, sendFile=None):
        print "Sending..."
        response = ""
        for header in headers:
            response += header + "\r\n"
        response += "\r\n"
        print "Response: '%s'" % (response)
        self.client.send(response)
        if sendFile:
            offset = 0
            while True:
                sent = sendfile(self.client.fileno(), sendFile.fileno(), offset, 65536)
                if sent == 0:
                    break  # EOF
                offset += sent
    
    
    
    