public class Token {
    TokenType type;
    String lexeme;
    int line;
    int column;


    Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Token: \"" + lexeme + "\"  Type: " + type + "  Line: " + line + "  Column: " + column;
    }



}