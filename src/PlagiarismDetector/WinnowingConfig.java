package PlagiarismDetector;

public class WinnowingConfig {
    private final int kGramSize;
    private final int windowSize;
    private final int hashBase;
    private final boolean normalizeIdentifiers;
    private final boolean ignoreComments;

    public WinnowingConfig(Builder builder) {
        this.kGramSize = builder.kGramSize;
        this.windowSize = builder.windowSize;
        this.hashBase = builder.hashBase;
        this.normalizeIdentifiers = builder.normalizeIdentifiers;
        this.ignoreComments = builder.ignoreComments;
    }

    public int getKGramSize() {
        return kGramSize;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getHashBase() {
        return hashBase;
    }

    public boolean shouldNormalizeIdentifiers() {
        return normalizeIdentifiers;
    }

    public boolean shouldIgnoreComments() {
        return ignoreComments;
    }

    /**
     * Builder class for WinnowingConfig.
     */

    public static class Builder {
        // Default values based on research recommendations
        private int kGramSize = 5;
        private int windowSize = 6;
        private int hashBase = 101;
        private boolean normalizeIdentifiers = true;
        private boolean ignoreComments = true;

        public Builder setKGramSize(int kGramSize) {
            this.kGramSize = kGramSize;
            return this;
        }

        public Builder setWindowSize(int windowSize) {
            this.windowSize = windowSize;
            return this;
        }

        public Builder setHashBase(int hashBase) {
            this.hashBase = hashBase;
            return this;
        }

        public Builder setNormalizeIdentifiers(boolean normalizeIdentifiers) {
            this.normalizeIdentifiers = normalizeIdentifiers;
            return this;
        }

        public Builder setIgnoreComments(boolean ignoreComments) {
            this.ignoreComments = ignoreComments;
            return this;
        }

        public WinnowingConfig build() {
            return new WinnowingConfig(this);
        }
    }
}
