package PlagiarismDetector.similarity;

import java.util.List;
import java.util.Set;

/**
 * Provides various similarity metrics for comparing code structures.
 */
public class SimilarityMetrics {

    /**
     * Calculates similarity between two hash strings.
     * The similarity is based on the longest common prefix.
     */
    public static double hashSimilarity(String hash1, String hash2) {
        if (hash1 == null || hash2 == null) {
            return 0.0;
        }

        if (hash1.equals(hash2)) {
            return 1.0;
        }

        // Compare character by character to find the common prefix length
        int maxLength = Math.min(hash1.length(), hash2.length());
        int commonPrefixLength = 0;

        for (int i = 0; i < maxLength; i++) {
            if (hash1.charAt(i) == hash2.charAt(i)) {
                commonPrefixLength++;
            } else {
                break;
            }
        }

        // Return ratio of common prefix to maximum possible length
        return (double) commonPrefixLength / maxLength;
    }

    /**
     * Calculates Jaccard similarity between two sets.
     * Jaccard similarity is the size of the intersection divided by the size of the union.
     */
    public static <T> double jaccardSimilarity(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null || set1.isEmpty() && set2.isEmpty()) {
            return 0.0;
        }

        // Count elements in intersection
        int intersectionSize = 0;
        for (T element : set1) {
            if (set2.contains(element)) {
                intersectionSize++;
            }
        }

        // Union size is sum of both sets minus the intersection
        int unionSize = set1.size() + set2.size() - intersectionSize;

        return (double) intersectionSize / unionSize;
    }

    /**
     * Calculates similarity between two sequences using longest common subsequence (LCS).
     */
    public static double sequenceSimilarity(List<String> sequence1, List<String> sequence2) {
        if (sequence1 == null || sequence2 == null ||
                sequence1.isEmpty() || sequence2.isEmpty()) {
            return 0.0;
        }

        // Calculate the length of the longest common subsequence
        int lcsLength = longestCommonSubsequenceLength(sequence1, sequence2);

        // Normalize by the length of the longer sequence
        int maxLength = Math.max(sequence1.size(), sequence2.size());
        return (double) lcsLength / maxLength;
    }

    /**
     * Computes the length of the longest common subsequence between two sequences.
     * Dynamic programming implementation.
     */
    private static int longestCommonSubsequenceLength(List<String> seq1, List<String> seq2) {
        int m = seq1.size();
        int n = seq2.size();

        // Create DP table
        int[][] dp = new int[m + 1][n + 1];

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (seq1.get(i - 1).equals(seq2.get(j - 1))) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }
}
