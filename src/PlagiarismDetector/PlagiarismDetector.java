package PlagiarismDetector;


/**
 * Main class for detecting plagiarism between code samples using the Winnowing algorithm.
 */
public class PlagiarismDetector {
    /**
     * Entry point for running the application.
     */
    public static void main(String[] args) {
        // Example usage with test sources
        String source1 = "public class Example {\n" +
                "    public static void main(String[] args) {\n" +
                "        int x = 10;\n" +
                "        int y = 20;\n" +
                "        int sum = x + y;\n" +
                "        System.out.println(\"Sum: \" + sum);\n" +
                "    }\n" +
                "}";

        String source2 = "public class Test {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = 10;\n" +
                "        int b = 20;\n" +
                "        int result = a + b;\n" +
                "        System.out.println(\"Result: \" + result);\n" +
                "    }\n" +
                "}";

        // Configure detector with recommended parameters for code comparison
        WinnowingConfig config = new WinnowingConfig.Builder()
                .setKGramSize(5)
                .setWindowSize(6)
                .setNormalizeIdentifiers(true)
                .setIgnoreComments(true)
                .build();

        WinnowingDetector detector = new WinnowingDetector(config);

        // Run basic comparison
        double similarity = detector.compareCode(source1, source2);
        System.out.println("Similarity: " + similarity + "%");

        // Run detailed analysis
        PlagiarismReport report = detector.analyzeCode(source1, source2);
        System.out.println(report);
    }
}