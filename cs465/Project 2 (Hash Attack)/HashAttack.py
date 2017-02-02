

import hashlib
import os
from random import randint

class HashAttack():
    def __init__(self, length, tests):
        self.length = length
        self.tests = tests
      
    def getRandomBytes(self):
        return os.urandom(randint(100, 1000))
    
    def getHash(self, r_bytes):
        return hashlib.sha1(r_bytes).hexdigest()[:self.length / 4] 
    
    def collisionTest(self):
        results = []
        for i in range(self.tests):
            results.append(self.collision())
        average = sum(results)/len(results)
        
        return ["Average: %d" % average, "Tests: %d" % self.tests, "Length: %d" % self.length]
    
    def collision(self):
        data = dict()
        n_bytes = self.getRandomBytes()
        count = 0
        
        while not self.getHash(n_bytes) in data or n_bytes == data[self.getHash(n_bytes)]:
            count += 1
            data[self.getHash(n_bytes)] = n_bytes
            n_bytes = self.getRandomBytes()
            
        print ["Count: %06d" % count, 
                "Data Length: %06d" % len(data), 
                "1st Len: %04d" % len(data[self.getHash(n_bytes)]),
                "2nd Len: %04d" % len(n_bytes)]
         
        return len(data)
    
    def preimageTest(self):
        results = []
        for i in range(self.tests):
            results.append(self.preimage())
        average = sum(results)/len(results)
        return ["Average: %d" % average, "Tests: %d" % self.tests, "Length: %d" % self.length]
    
    def preimage(self):
        o_bytes = self.getRandomBytes()
        n_bytes = self.getRandomBytes()
        count = 0
        while self.getHash(o_bytes) !=  self.getHash(n_bytes):
            count += 1
            n_bytes = self.getRandomBytes()
            
        print ["Count: %06d" % count, 
                "1st Len: %04d" % len(o_bytes),
                "2nd Len: %04d" % len(n_bytes)]

        return count
        
def main():
    
    length = 16
    tests = 20
    while length >= 4:
        ha = HashAttack(length, tests)
    
        print "** Collision Test Results ***"
        print ha.collisionTest()
        print ""
       
        print "** Preimage Test Results ***"
        print ha.preimageTest()
        print ""
        length -= 4


if __name__ == '__main__':
    main()
    pass