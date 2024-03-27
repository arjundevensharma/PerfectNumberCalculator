import java.util.*;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

public class PerfectNumber {

	public static void main(String[] args) throws IOException {
	    ArrayList<Integer> primes = primesWithSieveOfAtkin(82589933);
	    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

	    System.out.println("\n\n\nA perfect number is a number whose factors add up to itself. 6 is the smallest perfect number: 6 = 1+2+3");
	    System.out.println("As of now, there are ONLY 51 perfect numbers found. TCS has decided to join the grand hunt.");
	    System.out.print("\nPress ENTER to start the 2022 Great TCS Perfect Number Search...");
	    
	    System.in.read();
	    
	    System.out.println("\nStart Time: " + sdf.format(new Date()) + "\n");
	    
	    System.out.printf("%-5s %-25s %-15s %-25s %-25s%n", "#", "Perfect Number", "# of Digits", "Days Taken", "Date");

	    int count = 1;
	    long startTime = System.currentTimeMillis();
	    
	    // For the first perfect number which is 6
	    System.out.printf("%-5d %-25d %-15d %-25s %-25s%n", count++, 6, 1, "0d, 0hrs, 0min, 0sec", sdf.format(new Date()));
	    
	    for (int p : primes) {
	        if (isMersennePrime(p)) {
	            BigInteger perfectNumber = BigInteger.TWO.pow(p - 1).multiply(BigInteger.TWO.pow(p).subtract(BigInteger.ONE));

	            String scientificNotation = toScientificNotation(perfectNumber);

	            long endTime = System.currentTimeMillis();
	            double secondsTaken = (endTime - startTime) / 1000.0;

	            String timeFormat = formatTime(secondsTaken);

	            System.out.printf("%-5d %-25s %-15d %-25s %-25s%n", count++, scientificNotation, String.valueOf(perfectNumber).length(), timeFormat, sdf.format(new Date()));
	        }
	    }
	}

	public static String formatTime(double totalSeconds) {
	    int days = (int) totalSeconds / (60 * 60 * 24);
	    int hours = (int) (totalSeconds % (60 * 60 * 24)) / (60 * 60);
	    int minutes = (int) (totalSeconds % (60 * 60)) / 60;
	    int seconds = (int) (totalSeconds % 60);

	    return days + "d, " + hours + "hrs, " + minutes + "min, " + seconds + "sec";
	}

	public static String toScientificNotation(BigInteger bigInt) {
	    String strVal = bigInt.toString();
	    if (strVal.length() <= 13) return strVal;
	    return String.format("%c.%sE%d", strVal.charAt(0), strVal.substring(1, 5), strVal.length() - 1);
	}

    
    //Lucas Lehmer test checks if prime numbers as p in 2^p - 1 result in prime
    //if so, perfect number = 2^(p−1)·(2^p−1)
    public static boolean isMersennePrime(int p) {
        BigInteger initVal = BigInteger.valueOf(4);
        BigInteger mersennePrimeTypeFormula = BigInteger.TWO.pow(p).subtract(BigInteger.ONE); 

        for (int i = 0; i < p - 2; i++) {
        	initVal = initVal.multiply(initVal).subtract(BigInteger.TWO).mod(mersennePrimeTypeFormula);
        }
        
        return initVal.equals(BigInteger.ZERO);
    }
    
    //algorithm called the sieve of atkin algorithm quickly generates prime numbers
    //generated primes are later searched to see if they form mersenne primes
    public static ArrayList<Integer> primesWithSieveOfAtkin(int limit) {
    	
        boolean[] sieve = new boolean[limit + 1];
        Arrays.fill(sieve, false);
        
        sieve[2] = true;
        sieve[3] = true;
        
        int root = (int) Math.ceil(Math.sqrt(limit));

        for (int x = 1; x <= root; x++) {
            for (int y = 1; y <= root; y++) {
                int n = (4 * x * x) + (y * y);
                if (n <= limit && (n % 12 == 1 || n % 12 == 5)) {
                    sieve[n] ^= true;
                }
                
                n = (3 * x * x) + (y * y);
                if (n <= limit && n % 12 == 7) {
                    sieve[n] ^= true;
                }
                
                n = (3 * x * x) - (y * y);
                if (x > y && n <= limit && n % 12 == 11) {
                    sieve[n] ^= true;
                }
            }
        }

        for (int n = 5; n <= root; n++) {
            if (sieve[n]) {
                for (int k = n * n; k <= limit; k += n * n) {
                    sieve[k] = false;
                }
            }
        }

        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (sieve[i]) {
                primes.add(i);
            }
        }

        return primes;
    }
    
}