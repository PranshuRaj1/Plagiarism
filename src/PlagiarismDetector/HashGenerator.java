package PlagiarismDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates hash values for k-grams.
 */
public class HashGenerator {
    /**
     * Hash a list of k-grams using the specified base.
     */
    public static List<Long> hashKGrams(List<String> kgrams, int base) {
        List<Long> hashes = new ArrayList<>(kgrams.size());
        for (String kgram : kgrams) {
            hashes.add(computeHash(kgram, base));
        }
        return hashes;
    }

    /**
     * Compute hash for a single string using a Rabin-Karp style rolling hash.
     */
    private static long computeHash(String s, int base) {
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = (hash * base + c) & 0xFFFFFFFFL;
        }
        return hash;
    }
}
