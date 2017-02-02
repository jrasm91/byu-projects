
from Crypto.PublicKey import RSA

class MyRSA:
    def __init__(self):
        self.init()
        
    def init(self):
        rsaKey = RSA.generate(1024)
        self.p = getattr(rsaKey.key, 'p')
        self.q = getattr(rsaKey.key, 'q')
        self.n = self.p * self.q
        self.e = 65537 
        self.phi = (self.p - 1)*(self.q - 1)
        self.d = self.gcd(self.phi, self.e)
        
        assert len(bin(self.p)[2:]) == 512
        assert len(bin(self.q)[2:]) == 512
        
        print "p: " + str(self.p)
        print "q: " + str(self.q)
        print "n: " + str(self.n)
        print "e: " + str(self.e)
        print "d: " + str(self.d)
        
    def gcd(self, a, b):
        return [x % a for x in self.extendedGCD(a, b) if x*b % a == 1][0]
    
    def extendedGCD(self, a, b):
        if b == 0:
            return (1, 0)
        else:
            q = a/b
            r = a % b
            (s, t) = self.extendedGCD(b, r)
            return (t, s - q * t)

    def modexp(self, g, e, p):
        total = 1
        for char in bin(e)[2:][::-1]:
            if int(char) == 1:
                total = (total * g) % p
            g = (g * g) % p
        return total
    
    def encrypt(self, m):
        return self.modexp(m, self.e, self.n)
    
    def decrypt(self, m):
        return self.modexp(m, self.d, self.n)
    
    def rsa(self):
        print "RSA"
        m = long(raw_input("Encrypt: "))
        print "Encrypted: ", str(self.encrypt(m))
        m = long(raw_input("Decrypt: "))
        print "Decrypted: ", str(self.decrypt(m))

if __name__ == "__main__":
    MyRSA().rsa()
    
    
    
    
    
    