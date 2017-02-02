
from Crypto.PublicKey import RSA

def modexp(g, e, p):
    total = 1
    for char in bin(e)[2:][::-1]:
        if int(char) == 1:
            total = (total * g) % p
        g = (g * g) % p
    return total

def main():
#     rsaKey = RSA.generate(1024)
#     p = getattr(rsaKey.key, 'p')
#     e = getattr(rsaKey.key, 'e')
    
    g = 5;
    e = 65537
    p = 10420114435343421584900872011420348447007082906095422844459465548247386401885848518124279228981416500145014945974773331228264181981978897461511905365936923
    
    o = 9406890463958649390381812017005225534485428148067617393712064969323874797060768576142051572048714924021235657507328293062249085312038762526625076931404956
    
    print "base  : " + str(g)
    print "prime : " + str(p)
    print "secret: " + str(e)
    print "result: " + str(modexp(g, e, p))
    print "other : " + str(o)
    print "key   : " + str(modexp(o, e, p))
    print ""
    
    for e in range(1, 14):
        print "e: %d, t: %d" % (e, modexp(4, e, 497))
    print ""
    print "Length: " + str((len(bin(p)[2:])))

if __name__ == "__main__":
    main()
    
    
    
    
    
    