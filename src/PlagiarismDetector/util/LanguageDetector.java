package PlagiarismDetector.util;

import java.util.regex.Pattern;

/**
 * Utility class for detecting programming languages from source code.
 */
public class LanguageDetector {
    // Patterns for different languages
    private static final Pattern JAVA_PATTERN = Pattern.compile(
            "\\bclass\\s+\\w+|\\bpublic\\s+static\\s+void\\s+main|import\\s+java\\.|@Override\\b",
            Pattern.MULTILINE);

    private static final Pattern CPP_PATTERN = Pattern.compile(
            "#include\\s+<\\w+>|\\bstd::|\\bvector<|\\busing\\s+namespace\\s+std\\b|\\bint\\s+main\\s*\\(",
            Pattern.MULTILINE);

    /**
     * Detects the programming language of the given source code.
     *
     * @param sourceCode The source code to analyze
     * @return A string representing the detected language: "java", "cpp", or "unknown"
     */
    public static String detectLanguage(String sourceCode) {
        if (sourceCode == null || sourceCode.trim().isEmpty()) {
            return "unknown";
        }

        // Check for language-specific patterns
        if (JAVA_PATTERN.matcher(sourceCode).find()) {
            return "java";
        }

        if (CPP_PATTERN.matcher(sourceCode).find()) {
            return "cpp";
        }

        // Additional heuristics
        if (sourceCode.contains("{") && sourceCode.contains("}")) {
            // Code has curly braces - likely Java or C++
            if (sourceCode.contains("public class") || sourceCode.contains("interface")) {
                return "java";
            } else if (sourceCode.contains("cout") || sourceCode.contains("cin")) {
                return "cpp";
            } else {
                // Default to C++ if it has braces but no other clear indicators
                return "cpp";
            }
        }

        return "unknown";
    }
}
