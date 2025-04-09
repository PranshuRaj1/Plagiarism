// src/main/java/PlagiarismDetector/ast/parser/JavaASTParser.java
package PlagiarismDetector.ast.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import PlagiarismDetector.ast.model.NodeType;
import PlagiarismDetector.ast.model.UniversalASTNode;

/**
 * Parser for Java source code using JavaParser
 * Based on search result #3
 */
public class JavaASTParser implements ASTParser {

    @Override
    public UniversalASTNode parse(String sourceCode) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(sourceCode);
            return convertToUniversalAST(cu);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Java code", e);
        }
    }

    private UniversalASTNode convertToUniversalAST(Node node) {
        // Map JavaParser node types to our universal types
        NodeType type = mapNodeType(node);
        UniversalASTNode uNode = new UniversalASTNode(type, node.toString());

        // Add relevant attributes
        if (node.getRange().isPresent()) {
            uNode.addAttribute("startLine",
                    String.valueOf(node.getRange().get().begin.line));
            uNode.addAttribute("endLine",
                    String.valueOf(node.getRange().get().end.line));
        }

        // Process children recursively
        for (Node child : node.getChildNodes()) {
            uNode.addChild(convertToUniversalAST(child));
        }

        return uNode;
    }

    private NodeType mapNodeType(Node node) {
        String simpleName = node.getClass().getSimpleName();

        // Map Java-specific node types to universal types
        switch (simpleName) {
            case "MethodDeclaration": return NodeType.METHOD_DECLARATION;
            case "BlockStmt": return NodeType.BLOCK;
            case "IfStmt": return NodeType.IF_STATEMENT;
            case "ForStmt": return NodeType.FOR_LOOP;
            case "WhileStmt": return NodeType.WHILE_LOOP;
            case "ReturnStmt": return NodeType.RETURN_STATEMENT;
            case "BinaryExpr": return NodeType.BINARY_EXPRESSION;
            case "VariableDeclarator": return NodeType.VARIABLE_DECLARATION;
            default: return NodeType.UNKNOWN;
        }
    }
}
