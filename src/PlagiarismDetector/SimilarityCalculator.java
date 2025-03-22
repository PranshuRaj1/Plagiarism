package PlagiarismDetector;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculates similarity between document fingerprints using Jaccard similarity.
 */
public class SimilarityCalculator {
    public static double calculateJaccardSimilarity(Set<Long> set1, Set<Long> set2) {
        if (set1.isEmpty() && set2.isEmpty()) {
            return 100.0;
        }
        Set<Long> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<Long> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size() * 100.0;
    }
}
