package PlagiarismDetector.similarity;

import java.util.*;

import PlagiarismDetector.ast.model.UniversalASTNode;
import PlagiarismDetector.util.HashUtils;

/**
 * Compares two Abstract Syntax Trees for structural similarity.
 * Based on research from Papers #5, #10, and #14
 */
public class ASTComparator {

    /**
     * Compares two ASTs and returns a similarity score between 0.0 and 1.0
     *
     * @param node1 The first AST node
     * @param node2 The second AST node
     * @return A similarity score where 1.0 is identical and 0.0 is completely different
     */
    public double compare(UniversalASTNode node1, UniversalASTNode node2) {
        if (node1 == null || node2 == null) {
            return 0.0;
        }

        // Use a weighted combination of different similarity metrics
        double structuralSimilarity = compareStructure(node1, node2);
        double subtreeSimilarity = compareSubtrees(node1, node2);
        double sequenceSimilarity = compareNodeSequences(node1, node2);

        // Weights determined empirically from research papers
        return (0.5 * structuralSimilarity) +
                (0.3 * subtreeSimilarity) +
                (0.2 * sequenceSimilarity);
    }

    /**
     * Compares the structural hash of two AST nodes
     */
    private double compareStructure(UniversalASTNode node1, UniversalASTNode node2) {
        String hash1 = node1.getStructuralHash();
        String hash2 = node2.getStructuralHash();

        return SimilarityMetrics.hashSimilarity(hash1, hash2);
    }

    /**
     * Compares subtrees by finding the maximum matching subtree size
     */
    private double compareSubtrees(UniversalASTNode node1, UniversalASTNode node2) {
        // Get all subtrees from both ASTs
        Set<String> subtreeHashes1 = collectSubtreeHashes(node1);
        Set<String> subtreeHashes2 = collectSubtreeHashes(node2);

        // Calculate Jaccard similarity of the subtree sets
        return SimilarityMetrics.jaccardSimilarity(subtreeHashes1, subtreeHashes2);
    }

    /**
     * Compares the sequence of node types in pre-order traversal
     */
    private double compareNodeSequences(UniversalASTNode node1, UniversalASTNode node2) {
        List<String> sequence1 = collectNodeTypeSequence(node1);
        List<String> sequence2 = collectNodeTypeSequence(node2);

        return SimilarityMetrics.sequenceSimilarity(sequence1, sequence2);
    }

    /**
     * Collects hashes of all subtrees in the AST
     */
    private Set<String> collectSubtreeHashes(UniversalASTNode node) {
        Set<String> hashes = new HashSet<>();
        collectSubtreeHashesRecursive(node, hashes);
        return hashes;
    }

    /**
     * Helper method for recursive collection of subtree hashes
     */
    private void collectSubtreeHashesRecursive(UniversalASTNode node, Set<String> hashes) {
        // Add current node's hash
        hashes.add(node.getStructuralHash());

        // Recursively process children
        for (UniversalASTNode child : node.getChildren()) {
            collectSubtreeHashesRecursive(child, hashes);
        }
    }

    /**
     * Collects the sequence of node types in pre-order traversal
     */
    private List<String> collectNodeTypeSequence(UniversalASTNode node) {
        List<String> sequence = new ArrayList<>();
        collectNodeTypeSequenceRecursive(node, sequence);
        return sequence;
    }

    /**
     * Helper method for recursive collection of node type sequence
     */
    private void collectNodeTypeSequenceRecursive(UniversalASTNode node, List<String> sequence) {
        // Add current node's type
        sequence.add(node.getType().toString());

        // Recursively process children
        for (UniversalASTNode child : node.getChildren()) {
            collectNodeTypeSequenceRecursive(child, sequence);
        }
    }
}
