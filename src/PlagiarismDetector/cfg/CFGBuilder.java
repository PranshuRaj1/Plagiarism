// src/main/java/PlagiarismDetector/cfg/CFGBuilder.java
package PlagiarismDetector.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PlagiarismDetector.ast.model.NodeType;
import PlagiarismDetector.ast.model.UniversalASTNode;

/**
 * Builds Control Flow Graphs from ASTs
 * Based on search result #5 and #11
 */
public class CFGBuilder {

    public CFGNode build(UniversalASTNode root) {
        if (root == null) return null;

        // Start with method declarations
        List<UniversalASTNode> methods = findMethodDeclarations(root);
        Map<String, CFGNode> methodGraphs = new HashMap<>();

        // Build CFG for each method
        for (UniversalASTNode method : methods) {
            String methodName = extractMethodName(method);
            CFGNode entryNode = buildMethodCFG(method);
            methodGraphs.put(methodName, entryNode);
        }

        // For simplicity, return the CFG of the first method
        // In a real implementation, you'd have a more complex structure
        if (!methodGraphs.isEmpty()) {
            return methodGraphs.values().iterator().next();
        }

        return null;
    }

    private List<UniversalASTNode> findMethodDeclarations(UniversalASTNode root) {
        List<UniversalASTNode> methods = new ArrayList<>();
        findMethodsRecursive(root, methods);
        return methods;
    }

    private void findMethodsRecursive(UniversalASTNode node, List<UniversalASTNode> methods) {
        if (node.getType() == NodeType.METHOD_DECLARATION) {
            methods.add(node);
        }

        for (UniversalASTNode child : node.getChildren()) {
            findMethodsRecursive(child, methods);
        }
    }

    private String extractMethodName(UniversalASTNode methodNode) {
        // In a real implementation, you would extract the method name from the AST
        // For simplicity, just return a default value
        return "method";
    }

    private CFGNode buildMethodCFG(UniversalASTNode methodNode) {
        // Create entry node
        CFGNode entryNode = new CFGNode("ENTRY");

        // Find method body (usually a block node)
        UniversalASTNode bodyNode = findBodyNode(methodNode);
        if (bodyNode == null) return entryNode;

        // Process body statements
        CFGNode currentNode = entryNode;
        for (UniversalASTNode statement : bodyNode.getChildren()) {
            currentNode = processCFGStatement(statement, currentNode);
        }

        // Create exit node and connect
        CFGNode exitNode = new CFGNode("EXIT");
        currentNode.addSuccessor(exitNode);

        return entryNode;
    }

    private UniversalASTNode findBodyNode(UniversalASTNode methodNode) {
        for (UniversalASTNode child : methodNode.getChildren()) {
            if (child.getType() == NodeType.BLOCK) {
                return child;
            }
        }
        return null;
    }

    private CFGNode processCFGStatement(UniversalASTNode statement, CFGNode predecessor) {
        CFGNode stmtNode = new CFGNode(statement.getType().toString());
        predecessor.addSuccessor(stmtNode);

        // Handle control flow based on statement type
        switch (statement.getType()) {
            case IF_STATEMENT:
                return processIfStatement(statement, stmtNode);
            case FOR_LOOP:
            case WHILE_LOOP:
                return processLoop(statement, stmtNode);
            case RETURN_STATEMENT:
                // Return statements are terminal in a basic block
                return stmtNode;
            default:
                // Simple statement, just continue flow
                return stmtNode;
        }
    }

    private CFGNode processIfStatement(UniversalASTNode ifStmt, CFGNode ifNode) {
        // Find condition, then and else parts
        // In a real implementation, you would navigate the AST correctly
        // This is simplified

        // Create nodes for the branches
        CFGNode thenNode = new CFGNode("THEN");
        CFGNode elseNode = new CFGNode("ELSE");
        CFGNode mergeNode = new CFGNode("MERGE");

        // Connect condition to branches
        ifNode.addSuccessor(thenNode);
        ifNode.addSuccessor(elseNode);

        // Connect branches back to merge point
        thenNode.addSuccessor(mergeNode);
        elseNode.addSuccessor(mergeNode);

        return mergeNode;
    }

    private CFGNode processLoop(UniversalASTNode loopStmt, CFGNode loopNode) {
        // Similar to if statement, but with backward edges
        // Simplified implementation

        CFGNode bodyNode = new CFGNode("LOOP_BODY");
        CFGNode exitNode = new CFGNode("LOOP_EXIT");

        // Connect loop condition to body and exit
        loopNode.addSuccessor(bodyNode);
        loopNode.addSuccessor(exitNode);

        // Loop back from body to condition
        bodyNode.addSuccessor(loopNode);

        return exitNode;
    }
}
