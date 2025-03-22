package PlagiarismDetector;

import java.util.Set;
import java.util.List;

/**
 * Core implementation of the Winnowing algorithm.
 */
public class WinnowingDetector {
    private final WinnowingConfig config;

    public WinnowingDetector(WinnowingConfig config) {
        this.config = config;
    }

    /**
     * Compare two code samples and return a similarity percentage.
     */
    public double compareCode(String source1, String source2) {
        // Generate fingerprints for both code samples
        DocumentProfile profile1 = generateProfile(source1);
        DocumentProfile profile2 = generateProfile(source2);

        // Calculate similarity using Jaccard similarity
        return SimilarityCalculator.calculateJaccardSimilarity(
                profile1.getFingerprints(),
                profile2.getFingerprints()
        );
    }

    /**
     * Generate a complete document profile from source code.
     */
    private DocumentProfile generateProfile(String source) {
        // Tokenize using the TokenExtractor
        TokenExtractor tokenExtractor = new TokenExtractor(config);
        List<String> tokens = tokenExtractor.extractTokens(source);

        // Generate k-grams
        List<String> kgrams = KGramGenerator.generateKGrams(tokens, config.getKGramSize());

        // Hash k-grams
        List<Long> hashes = HashGenerator.hashKGrams(kgrams, config.getHashBase());

        // Apply the winnowing algorithm to select fingerprints
        Set<Long> fingerprints = FingerprintSelector.selectFingerprints(hashes, config.getWindowSize());

        return new DocumentProfile(tokens, kgrams, fingerprints);
    }

    /**
     * Perform detailed plagiarism analysis between two code samples.
     */
    public PlagiarismReport analyzeCode(String source1, String source2) {
        // Generate profiles
        DocumentProfile profile1 = generateProfile(source1);
        DocumentProfile profile2 = generateProfile(source2);

        // Find matching sections
        List<MatchedSection> matchedSections =
                MatchDetector.findMatchingSections(profile1.getKGrams(), profile2.getKGrams());

        // Calculate similarity using Jaccard similarity
        double similarity = SimilarityCalculator.calculateJaccardSimilarity(
                profile1.getFingerprints(),
                profile2.getFingerprints()
        );

        return new PlagiarismReport(
                similarity,
                matchedSections,
                profile1.getFingerprints().size(),
                profile2.getFingerprints().size()
        );
    }
}
