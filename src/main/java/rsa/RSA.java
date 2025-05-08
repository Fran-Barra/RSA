package rsa;

public class RSA {

    public static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long modInverse(long e, long phi) {
        long t = 0, newT = 1;
        long r = phi, newR = e;

        while (newR != 0) {
            long quotient = r / newR;

            long tempT = t;
            t = newT;
            newT = tempT - quotient * newT;

            long tempR = r;
            r = newR;
            newR = tempR - quotient * newR;
        }

        if (r > 1) throw new ArithmeticException("e is not invertible");
        if (t < 0) t += phi;

        return t;
    }

    public static long fastExp(long base, long exponent, long mod) {
        long result = 1;
        base = base % mod;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent >>= 1;
        }

        return result;
    }

    public static void main(String[] args) {
        long p = 61;
        long q = 53;
        long n = p * q;
        long phi = (p - 1) * (q - 1);

        long e = 17;
        long d = modInverse(e, phi);

        System.out.println("Public key: (n=" + n + ", e=" + e + ")");
        System.out.println("Private key: d=" + d);

        long message = 65;
        long encrypted = fastExp(message, e, n);
        System.out.println("Encrypted message: " + encrypted);

        long decrypted = fastExp(encrypted, d, n);
        System.out.println("Decrypted message: " + decrypted);
    }
}