package PlagiarismDetector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Selects document fingerprints using the Winnowing algorithm.
 */
public class FingerprintSelector {
    /**
     * Select fingerprints from hash values using a sliding window.
     */
    public static Set<Long> selectFingerprints(List<Long> hashes, int windowSize) {
        Set<Long> fingerprints = new HashSet<>();
        if (hashes.isEmpty()) {
            return fingerprints;
        }
        if (hashes.size() < windowSize) {
            fingerprints.addAll(hashes);
            return fingerprints;
        }
        for (int i = 0; i <= hashes.size() - windowSize; i++) {
            int minPos = i;
            long minHash = hashes.get(i);
            for (int j = i + 1; j < i + windowSize; j++) {
                if (hashes.get(j) < minHash) {
                    minHash = hashes.get(j);
                    minPos = j;
                }
            }
            fingerprints.add(minHash);
            if (minPos > i) {
                i = minPos - 1;
            }
        }
        return fingerprints;
    }
}
