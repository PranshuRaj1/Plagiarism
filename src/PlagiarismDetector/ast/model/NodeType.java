package PlagiarismDetector.ast.model;

/**
 * Enumeration of universal AST node types.
 * Provides a language-agnostic classification of code constructs.
 */
public enum NodeType {
    // Program structure
    COMPILATION_UNIT,
    PACKAGE_DECLARATION,
    IMPORT_DECLARATION,

    // Type declarations
    CLASS_DECLARATION,
    INTERFACE_DECLARATION,
    ENUM_DECLARATION,

    // Class members
    FIELD_DECLARATION,
    METHOD_DECLARATION,
    CONSTRUCTOR_DECLARATION,

    // Statements
    BLOCK,
    IF_STATEMENT,
    FOR_LOOP,
    WHILE_LOOP,
    DO_WHILE_LOOP,
    SWITCH_STATEMENT,
    CASE_STATEMENT,
    TRY_STATEMENT,
    CATCH_CLAUSE,
    THROW_STATEMENT,
    RETURN_STATEMENT,
    BREAK_STATEMENT,
    CONTINUE_STATEMENT,
    EXPRESSION_STATEMENT,

    // Expressions
    BINARY_EXPRESSION,
    UNARY_EXPRESSION,
    CONDITIONAL_EXPRESSION,
    ASSIGNMENT_EXPRESSION,
    METHOD_INVOCATION,
    OBJECT_CREATION,
    ARRAY_ACCESS,

    // Variables and literals
    VARIABLE_DECLARATION,
    VARIABLE_REFERENCE,
    LITERAL,

    // Other
    UNKNOWN;

    /**
     * Maps a string representation to its corresponding NodeType.
     *
     * @param typeString The string representation of a node type
     * @return The NodeType enum value, or UNKNOWN if not found
     */
    public static NodeType fromString(String typeString) {
        if (typeString == null || typeString.isEmpty()) {
            return UNKNOWN;
        }

        try {
            return valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    /**
     * Checks if this node type represents a control flow statement.
     *
     * @return true if this is a control flow node, false otherwise
     */
    public boolean isControlFlow() {
        return this == IF_STATEMENT ||
                this == FOR_LOOP ||
                this == WHILE_LOOP ||
                this == DO_WHILE_LOOP ||
                this == SWITCH_STATEMENT ||
                this == RETURN_STATEMENT ||
                this == BREAK_STATEMENT ||
                this == CONTINUE_STATEMENT;
    }
}
