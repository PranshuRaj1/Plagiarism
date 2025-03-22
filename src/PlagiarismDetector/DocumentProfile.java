package PlagiarismDetector;

import java.util.List;
import java.util.Set;

/**
 * Represents a document's profile for plagiarism detection.
 * Stores intermediate data such as tokens, k-grams, and fingerprints.
 */
public class DocumentProfile {
    private final List<String> tokens;
    private final List<String> kGrams;
    private final Set<Long> fingerprints;

    public DocumentProfile(List<String> tokens, List<String> kGrams, Set<Long> fingerprints) {
        this.tokens = tokens;
        this.kGrams = kGrams;
        this.fingerprints = fingerprints;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public List<String> getKGrams() {
        return kGrams;
    }

    public Set<Long> getFingerprints() {
        return fingerprints;
    }
}
