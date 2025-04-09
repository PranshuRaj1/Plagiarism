// src/main/java/PlagiarismDetector/ast/visitor/NormalizationVisitor.java
package PlagiarismDetector.ast.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import PlagiarismDetector.ast.model.NodeType;
import PlagiarismDetector.ast.model.UniversalASTNode;

/**
 * Visitor that normalizes AST by standardizing variable names and structures
 * Based on research from Paper #5 and #10
 */
public class NormalizationVisitor {
    private final Map<String, String> variableMap = new HashMap<>();
    private final AtomicInteger varCounter = new AtomicInteger(1);

    public UniversalASTNode normalize(UniversalASTNode node) {
        // Create a new normalized node
        UniversalASTNode normalizedNode = new UniversalASTNode(node.getType(), node.getValue());

        // Normalize based on node type
        if (node.getType() == NodeType.VARIABLE_DECLARATION ||
                node.getType() == NodeType.VARIABLE_REFERENCE) {

            // Standardize variable names
            String varName = node.getValue();
            String normalizedName = variableMap.computeIfAbsent(varName,
                    k -> "var" + varCounter.getAndIncrement());

            normalizedNode = new UniversalASTNode(node.getType(), normalizedName);
        } else if (node.getType() == NodeType.METHOD_DECLARATION) {
            // Reset variable mapping for each method (local scope)
            variableMap.clear();
            varCounter.set(1);
        }

        // Copy attributes that don't need normalization
        for (Map.Entry<String, String> entry : node.getAttributes().entrySet()) {
            normalizedNode.addAttribute(entry.getKey(), entry.getValue());
        }

        // Recursively normalize children
        for (UniversalASTNode child : node.getChildren()) {
            normalizedNode.addChild(normalize(child));
        }

        return normalizedNode;
    }
}
