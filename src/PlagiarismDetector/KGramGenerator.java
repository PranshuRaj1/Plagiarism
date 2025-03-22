package PlagiarismDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates k-grams from token sequences.
 */
public class KGramGenerator {
    /**
     * Generate k-grams from a list of tokens.
     */
    public static List<String> generateKGrams(List<String> tokens, int k) {
        List<String> kgrams = new ArrayList<>();
        if (tokens.size() < k) {
            return kgrams;
        }
        for (int i = 0; i <= tokens.size() - k; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < k; j++) {
                sb.append(tokens.get(i + j));
            }
            kgrams.add(sb.toString());
        }
        return kgrams;
    }
}
