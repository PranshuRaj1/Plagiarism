package PlagiarismDetector.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import PlagiarismDetector.ast.model.NodeType;
import PlagiarismDetector.ast.model.UniversalASTNode;
import PlagiarismDetector.util.HashUtils;

/**
 * Visitor that computes various hashes for AST nodes.
 * Used for efficient comparison of code structures.
 */
public class HashingVisitor {

    /**
     * Computes a structural hash for the given AST node.
     * The hash is deterministic and captures the tree structure.
     *
     * @param node The AST node to hash
     * @return The structural hash as a string
     */
    public String computeStructuralHash(UniversalASTNode node) {
        if (node == null) {
            return "";
        }

        StringBuilder hashBuilder = new StringBuilder();

        // Add the node type
        hashBuilder.append(node.getType().toString());
        hashBuilder.append(":");

        // Add hashes of all children, maintaining order
        for (UniversalASTNode child : node.getChildren()) {
            hashBuilder.append(computeStructuralHash(child));
            hashBuilder.append(";");
        }

        return HashUtils.sha256(hashBuilder.toString());
    }

    /**
     * Computes k-grams of node types for the given AST.
     * Used for winnowing-based similarity detection.
     *
     * @param node The root node of the AST
     * @param k The size of each k-gram
     * @return A list of hashed k-grams
     */
    public List<Integer> computeKGramHashes(UniversalASTNode node, int k) {
        // First collect all node types in pre-order traversal
        List<String> nodeTypes = new ArrayList<>();
        collectNodeTypes(node, nodeTypes);

        // Then create k-grams
        List<Integer> kGramHashes = new ArrayList<>();
        if (nodeTypes.size() < k) {
            return kGramHashes;
        }

        for (int i = 0; i <= nodeTypes.size() - k; i++) {
            StringBuilder kGram = new StringBuilder();
            for (int j = 0; j < k; j++) {
                kGram.append(nodeTypes.get(i + j)).append(":");
            }

            // Hash the k-gram
            kGramHashes.add(HashUtils.murmurHash(kGram.toString()));
        }

        return kGramHashes;
    }

    /**
     * Helper method to collect node types in pre-order traversal.
     */
    private void collectNodeTypes(UniversalASTNode node, List<String> nodeTypes) {
        if (node == null) {
            return;
        }

        // Add current node type
        nodeTypes.add(node.getType().toString());

        // Process children
        for (UniversalASTNode child : node.getChildren()) {
            collectNodeTypes(child, nodeTypes);
        }
    }
}
