package PlagiarismDetector;

/**
 * Enumeration of plagiarism levels.
 */
public enum PlagiarismLevel {
    LOW("Low similarity - likely not plagiarism."),
    MODERATE("Moderate similarity - possible plagiarism. Manual review recommended."),
    HIGH("High similarity - strong indication of plagiarism.");

    private final String description;

    PlagiarismLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
