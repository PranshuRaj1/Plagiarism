package PlagiarismDetector;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts tokens from source code using the Lexer.
 */
public class TokenExtractor {
    private final WinnowingConfig config;

    public TokenExtractor(WinnowingConfig config) {
        this.config = config;
    }

    /**
     * Extract and normalize tokens from source code.
     */
    public List<String> extractTokens(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();
        return normalizeTokens(tokens);
    }

    /**
     * Normalize token objects to strings based on configuration.
     */
    private List<String> normalizeTokens(List<Token> tokens) {
        List<String> normalizedTokens = new ArrayList<>();
        for (Token token : tokens) {
            if (config.shouldIgnoreComments() &&
                    (token.type == TokenType.COMMENT ||
                            token.type == TokenType.PREPROCESSOR ||
                            token.type == TokenType.END_OF_FILE)) {
                continue;
            }
            if (config.shouldNormalizeIdentifiers() && token.type == TokenType.IDENTIFIER) {
                normalizedTokens.add("ID");
            } else {
                normalizedTokens.add(token.lexeme);
            }
        }
        return normalizedTokens;
    }
}
