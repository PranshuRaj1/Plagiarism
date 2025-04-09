package PlagiarismDetector.core;

import PlagiarismDetector.WinnowingConfig;
import PlagiarismDetector.WinnowingDetector;

public class PlagiarismDetector {

    // Assume you have a CFGAnalyzer instance defined somewhere.
    private CFGAnalyzer cfgAnalyzer = new CFGAnalyzer();

    public double compareSubmissions(String code1, String code2) {
        // Create configuration for token-based comparison.
        WinnowingConfig config = new WinnowingConfig.Builder()
                .setKGramSize(7)
                .setWindowSize(4)
                .setHashAlgorithm("murmur3")
                .setNormalizeIdentifiers(true)
                .setIgnoreComments(true)
                .build();

        // Create an instance of WinnowingDetector.
        WinnowingDetector detector = new WinnowingDetector(config);

        // Token-based similarity.
        double tokenSimilarity = detector.compareCode(code1, code2);

        // Structure-based similarity using your CFG analyzer.
        double structuralSimilarity = cfgAnalyzer.compareCode(code1, code2);

        // Combined score with weighting (30% token-based, 70% structural).
        return (0.3 * tokenSimilarity) + (0.7 * structuralSimilarity);
    }
}
