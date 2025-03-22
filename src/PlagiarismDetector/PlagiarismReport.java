package PlagiarismDetector;

import java.util.List;

/**
 * Detailed plagiarism analysis report.
 */
public class PlagiarismReport {
    private final double similarityScore;
    private final List<MatchedSection> matchedSections;
    private final int fingerprints1Count;
    private final int fingerprints2Count;

    public PlagiarismReport(double similarityScore, List<MatchedSection> matchedSections,
                            int fingerprints1Count, int fingerprints2Count) {
        this.similarityScore = similarityScore;
        this.matchedSections = matchedSections;
        this.fingerprints1Count = fingerprints1Count;
        this.fingerprints2Count = fingerprints2Count;
    }

    public PlagiarismLevel getPlagiarismLevel() {
        if (similarityScore < 20) {
            return PlagiarismLevel.LOW;
        } else if (similarityScore < 50) {
            return PlagiarismLevel.MODERATE;
        } else {
            return PlagiarismLevel.HIGH;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plagiarism Analysis Report:\n");
        sb.append("Similarity Score: ").append(String.format("%.2f", similarityScore)).append("%\n");
        sb.append("Document 1 Fingerprints: ").append(fingerprints1Count).append("\n");
        sb.append("Document 2 Fingerprints: ").append(fingerprints2Count).append("\n");
        sb.append("Matching Sections: ").append(matchedSections.size()).append("\n");
        if (!matchedSections.isEmpty()) {
            sb.append("\nDetailed Matching Sections:\n");
            for (int i = 0; i < matchedSections.size(); i++) {
                sb.append(i + 1).append(". ").append(matchedSections.get(i)).append("\n");
            }
        }
        sb.append("\nAssessment: ").append(getPlagiarismLevel().getDescription());
        return sb.toString();
    }
}
