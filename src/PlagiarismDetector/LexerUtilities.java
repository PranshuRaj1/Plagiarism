package PlagiarismDetector;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for lexer functions.
 */
public class LexerUtilities {
    private static final Set<String> keywords = new HashSet<>();

    static {
        // Combine C++ and Java keywords for simplicity.
        keywords.add("int");
        keywords.add("float");
        keywords.add("double");
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("for");
        keywords.add("return");
        keywords.add("class");
        keywords.add("public");
        keywords.add("private");
        keywords.add("protected");
    }

    public static boolean isKeyword(String lexeme) {
        return keywords.contains(lexeme);
    }
}
