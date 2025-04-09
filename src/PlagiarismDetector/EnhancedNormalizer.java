package PlagiarismDetector;

public class EnhancedNormalizer {
    /**
     * Applies a series of normalization steps to the source code.
     */
    public static String normalize(String code) {
        // Step 1: Normalize loop structures
        code = LoopNormalizer.normalizeLoops(code);

        // Step 2: Normalize variable names
        code = VariableNormalizer.normalizeVariables(code);

        // Step 3: Normalize mathematical operations
        // Example: Replace occurrences of 'totalOperations' or 'totalLevels' with 'counter'
        code = code.replaceAll("\\b(totalOperations|totalLevels)\\b", "counter");
        // Example: Convert "+= someValue;" to "= counter + someValue;"
        code = code.replaceAll("\\+=\\s*(.*?);", "= counter + $1;");

        // Step 4: Remove redundant type declarations (e.g., "long long" to "long")
        code = code.replaceAll("long\\s+long", "long");

        return code;
    }
}
