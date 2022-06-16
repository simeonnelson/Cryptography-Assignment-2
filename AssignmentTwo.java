import java.math.BigInteger;
import java.io.*;
import java.util.*;

public class AssignmentTwo
{
    public static void main(String[] args)
    {
        String message = "Encryption Test.";

        BigInteger[] key = keyGen();
        BigInteger[] pubKey = { key[0], key[1] };
        BigInteger priKey = key[2];

        BigInteger encryption = encrypt(message, pubKey);
        String decryption = decrypt(encryption, pubKey, priKey);

        System.out.println("Message: " + message);
        System.out.println("Encryption: " + encryption);
        System.out.println("Decryption: " + decryption);
    }

    public static BigInteger[] keyGen()
    {
        // find p and q
        Random randInt = new Random();
        BigInteger p = BigInteger.probablePrime(512, randInt);
        randInt = new Random();
        BigInteger q = BigInteger.probablePrime(512, randInt);

        // find n
        BigInteger n = p.multiply(q);

        // find phi(n) = (p-1)(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // find public exponent e
        randInt = new Random();
        BigInteger e;
        do 
        {
			e = new BigInteger(1024, randInt);
			while (e.min(phi).equals(phi)) 
            { 
				e = new BigInteger(1024, randInt);
			}
		}
        while(!gcd(e, phi).equals(BigInteger.ONE)); 

        // find d
        BigInteger d = e.modInverse(phi);

        // create key for return value
        BigInteger[] pubKey = {n,e,d};
        return pubKey;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) 
    {
		if (b.equals(BigInteger.ZERO)) {
			return a;
		} else {
			return gcd(b, a.mod(b));
		}
	}

    public static BigInteger encrypt(String text, BigInteger[] pubKey)
    {
        // convert string to big int
        BigInteger message = new BigInteger(text.getBytes());

        // encrypt and return
        return message.modPow(pubKey[1], pubKey[0]);
    }

    public static String decrypt(BigInteger message, BigInteger[] pubKey, BigInteger priKey)
    {
        // decrypt
        BigInteger decrypt = message.modPow(priKey, pubKey[0]);

        // convert to string and return
        return new String(decrypt.toByteArray());
    }
}