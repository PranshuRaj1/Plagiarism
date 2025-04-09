// src/main/java/PlagiarismDetector/ast/model/UniversalASTNode.java
package PlagiarismDetector.ast.model;

import PlagiarismDetector.util.HashUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A language-agnostic representation of an AST node
 * Based on research from Paper #4 and #6
 */
public class UniversalASTNode {
    private NodeType type;
    private String value;
    private List<UniversalASTNode> children;
    private Map<String, String> attributes;
    private String structuralHash;  // For efficient comparison

    public UniversalASTNode(NodeType type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
        this.attributes = new HashMap<>();
    }

    // Getters and setters
    public NodeType getType() { return type; }
    public String getValue() { return value; }
    public List<UniversalASTNode> getChildren() { return children; }
    public Map<String, String> getAttributes() { return attributes; }

    public void addChild(UniversalASTNode child) {
        this.children.add(child);
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    // Hash computation based on structure (Paper #10)
    public String getStructuralHash() {
        if (structuralHash == null) {
            structuralHash = computeStructuralHash();
        }
        return structuralHash;
    }

    private String computeStructuralHash() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());

        // Add children hashes in order
        for (UniversalASTNode child : children) {
            sb.append(child.getStructuralHash());
        }

        return HashUtils.sha256(sb.toString());
    }
}
