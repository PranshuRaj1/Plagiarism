import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lexer {
    private final   String input;
    private  int position;
    private  int start; // start index of the current token
    private  int line ;
    private  int column;

    private final Set<String> cppKeywords;
    private final Set<String> javaKeywords;
    private final Set<String> operators;

    public Lexer(String source) {
        this.input = source;
        this.position = 0;
        this.start = 0;
        this.line = 1;
        this.column = 1;

        cppKeywords = new HashSet<>();
        cppKeywords.add("int"); cppKeywords.add("float");
        cppKeywords.add("double"); cppKeywords.add("if");
        cppKeywords.add("else"); cppKeywords.add("while");
        cppKeywords.add("for"); cppKeywords.add("return");
        cppKeywords.add("class");

        javaKeywords = new HashSet<>();
        javaKeywords.add("int"); javaKeywords.add("float");
        javaKeywords.add("double"); javaKeywords.add("if");
        javaKeywords.add("else"); javaKeywords.add("while");
        javaKeywords.add("for"); javaKeywords.add("return");
        javaKeywords.add("class"); javaKeywords.add("public");
        javaKeywords.add("private"); javaKeywords.add("protected");

        operators = new HashSet<>();
        // Single-character operators
        operators.add("+"); operators.add("-"); operators.add("*");
        operators.add("/"); operators.add("="); operators.add("!");
        operators.add("<"); operators.add(">"); operators.add("%");
        operators.add("&"); operators.add("|"); operators.add("^");
        operators.add("~");
    }

    // Checks if we've reached the end of input.
    private boolean isAtEnd() {
        return position >= input.length();
    }

    // returns the current character without advancing

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }

        return input.charAt(position);
    }

    // Returns the next character (lookahead) without advancing.
    private char peekNext() {
        if (position + 1 >= input.length()) return '\0';
        return input.charAt(position + 1);
    }

    // Advances and returns the current character
    private char advance() {
        char curr = input.charAt(position++);
        if (curr == '\n') {
            line++;
            column = 1;
        }
        else {
            column++;
        }

        return curr;
    }

    // Skips whitespace characters
    private void skipWhitespace() {
        while (!isAtEnd() && Character.isWhitespace(peek())) {
            advance();
        }
    }

    // Creates a token from start to current position
    private Token makeToken(TokenType type) {
        int tokenColumn = column - (position - start);
        return new Token(
                type,
                input.substring(start,position),
                line,
                tokenColumn
        );
    }

    private Token scanIdentifier() {
        while (!isAtEnd() &&
                (Character.isLetterOrDigit(peek()) ||
                 peek() == '_'       )) {
            advance();
        }

        String lexeme = input.substring(start, position);
        TokenType type = TokenType.IDENTIFIER;

        if (cppKeywords.contains(lexeme) ||
        javaKeywords.contains(lexeme)) {
            type = TokenType.KEYWORD;
        }

        int tokenColumn = column - (position - start);

        return new Token(type,
                lexeme,
                line,
                tokenColumn);
    }

    // Scans a numeric literal.
    private Token scanNumber() {
        TokenType type = TokenType.INTEGER;
        while (!isAtEnd() && Character.isDigit(peek())) {
            advance();
        }
        // Check for a floating-point literal.
        if (peek() == '.' && Character.isDigit(peekNext())) {
            type = TokenType.FLOAT;
            advance(); // consume the '.'
            while (!isAtEnd() && Character.isDigit(peek())) {
                advance();
            }
        }
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);
        return new Token(type, lexeme, line, tokenColumn);
    }

    // Scans a string literal (handles escape sequences).
    private Token scanString() {
        // Assumes the opening double quote was already consumed.
        while (!isAtEnd() && peek() != '"') {
            if (peek() == '\\') { // skip escape sequence
                advance();
            }
            advance();
        }
        if (isAtEnd()) {
            throw new RuntimeException("Unterminated string literal at line " + line);
        }
        advance(); // consume the closing quote
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);
        return new Token(TokenType.STRING, lexeme, line, tokenColumn);
    }

    // Scans a character literal.
    private Token scanChar() {
        // Assumes the opening single quote was already consumed.
        if (peek() == '\\') {
            advance(); // skip the escape character
            advance(); // consume the escaped char
        } else {
            advance();
        }
        if (peek() != '\'') {
            throw new RuntimeException("Unterminated char literal at line " + line);
        }
        advance(); // consume closing single quote
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);
        return new Token(TokenType.CHAR, lexeme, line, tokenColumn);
    }

    // Scans an operator token.
    private Token scanOperator() {
        // For simplicity, consider operators as one-character tokens.
        while (!isAtEnd() && operators.contains(String.valueOf(peek()))) {
            advance();
        }
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);
        return new Token(TokenType.OPERATOR, lexeme, line, tokenColumn);
    }

    // Scans a comment (supports single-line and nested multi-line).
    private Token scanComment() {
        if (peek() == '/') {
            // Single-line comment.
            while (!isAtEnd() && peek() != '\n') {
                advance();
            }
        } else if (peek() == '*') {
            // Multi-line comment with nesting.
            advance(); // consume the '*' after '/'
            int nesting = 1;
            while (nesting > 0 && !isAtEnd()) {
                if (peek() == '/' && peekNext() == '*') {
                    nesting++;
                    advance();
                } else if (peek() == '*' && peekNext() == '/') {
                    nesting--;
                    advance();
                }
                advance();
            }
        }
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);
        return new Token(TokenType.COMMENT, lexeme, line, tokenColumn);
    }


    // Scans a preProcessor directive (C++)
    private Token scanPreProcessor() {
        while (!isAtEnd() && peek() != '\n') {
            advance();
        }
        String lexeme = input.substring(start, position);
        int tokenColumn = column - (position - start);

        return new Token(TokenType.PREPROCESSOR,
                lexeme,
                line,
                tokenColumn);
    }

    // Dispatches scanning of the next token
    private Token scanToken() {
        skipWhitespace();
        start = position;

        if (isAtEnd()) {
            return new Token(TokenType.END_OF_FILE,
                             "",line,column   );
        }

        char c = advance();

        if (Character.isLetter(c) || c == '_') {
            return scanIdentifier();
        }
        else if (Character.isDigit(c)) {
            return scanNumber();
        }

        else {
            switch (c) {
                case '"':
                    return scanString();
                case '\'':
                    return scanChar();
                case '/':
                    if (!isAtEnd() && (peek() == '/' || peek() == '*')) {
                        return scanComment();
                    } else {
                        return scanOperator();
                    }
                case '#':
                    return scanPreProcessor();
                default:
                    if (operators.contains(String.valueOf(c))) {
                        return scanOperator();
                    }
                    // Treat any other single character as a punctuator.
                    return new Token(TokenType.PUNCTUATOR, String.valueOf(c), line, column - 1);
            }
        }
    }

    // Tokenizes the entire input and returns a list of tokens
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (true) {
            Token token = scanToken();

            tokens.add(token);

            if (token.type == TokenType.END_OF_FILE) {
                break;
            }
        }
        return tokens;
    }

    // Example usage.
//    public static void main(String[] args) {
//        String source = "#include <iostream>\n" +
//                "// This is a single-line comment.\n" +
//                "int main() {\n" +
//                "    int number = 42;\n" +
//                "    float value = 3.14;\n" +
//                "    std::string text = \"Hello, world!\";\n" +
//                "    /* This is a \n" +
//                "       multi-line comment */\n" +
//                "    return 0;\n" +
//                "}\n";
//
//        Lexer lexer = new Lexer(source);
//        List<Token> tokens = lexer.tokenize();
//        for (Token token : tokens) {
//            System.out.println(token);
//        }
//    }

}
