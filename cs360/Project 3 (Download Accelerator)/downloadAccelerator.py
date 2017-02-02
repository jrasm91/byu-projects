
import argparse
import requests
import threading
from time import time

DEBUG = False

DEFAULT_THREADS = 5
DEFAULT_URL = 'http://ilab.cs.byu.edu/zappala/files/design-philosophy-sigcomm-88.pdf'
# DEFAULT_URL = 'http://ilab.cs.byu.edu/zappala/files/poster.pdf'
# DEFAULT_URL = 'http://ilab.cs.byu.edu/zappala/files/Delta-Rae-Morning-Comes-Live.mp3'

class Downloader:
    def __init__(self):
        self.parse_arguments()
#         self.threadCount
#         self.url

    def parse_arguments(self):
        parser = argparse.ArgumentParser(prog='Downloader Accelerator', description='A simple script that downloads a url', add_help=True)
        parser.add_argument('-n', '--threadCount', type=int, action='store', help='Specify the number of threadCount to download url with',default=DEFAULT_THREADS)
        parser.add_argument('-d', '--debug', type=int, action='store', help='Flag to enable debug',default=False)
        parser.add_argument('url', type=str, action='store', help='Specify url to download')
        args = parser.parse_args()
        self.threadCount = args.threadCount
        self.url = args.url
        if 'http://' not in self.url:
            self.url = 'http://' + self.url
        if DEBUG:
            self.url = DEFAULT_URL
            
    def download(self):
        startClock = time()
        size = long(requests.head(self.url).headers['content-length'])
        print "orig-size: %s" % (size)
        if DEBUG:
            print "content=length: %s" % (size)
        threads = []
        
        for i in range(0, self.threadCount):
            (start, end) = self.getStartEnd(i, size)
            print "SE: (%s, %s)" % (start, end)
            d = DownLoadThread(self.url, start, end)
            threads.append(d)
       
        for t in threads:
            t.start()
            
        fileName = self.url.split('/')[-1]
        with open(fileName, "wb") as f:
            for t in threads:
                t.join()
                f.write(t.content)
            f.close()
            
        endClock = time()
        delta = endClock - startClock
        
        print '%s %s %s %s' % (self.url, self.threadCount, str(size), delta)
        
    def getStartEnd(self, i, size):
        part = size/self.threadCount
        start = i * part
        end = (i + 1) * part - 1
        if size - end < part:
            end = size   
        return (start, end)

class DownLoadThread(threading.Thread):
    def __init__(self,url,startNum, endNum):
        self.url = url
        threading.Thread.__init__(self)
        self.content = None
        self.startNum = startNum
        self.endNum = endNum

    def run(self):
        r = requests.get(self.url, headers={'Accept-Encoding': '', 'Range': 'bytes=%s-%s' % (self.startNum, self.endNum)})
        if DEBUG:
            print "% 8s - % 8s --> % 8s (%s)" % (self.startNum, self.endNum, self.endNum - self.startNum, r.headers['content-length'])
        self.content = r.content
  
if __name__ == '__main__':
    d = Downloader()
    d.download()    
    
    