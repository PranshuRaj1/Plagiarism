// src/main/java/PlagiarismDetector/similarity/CFGComparator.java
package PlagiarismDetector.similarity;

import java.util.*;

import PlagiarismDetector.cfg.CFGNode;
import PlagiarismDetector.cfg.CFGEdge;

/**
 * Compares Control Flow Graphs for similarity
 * Based on topology-aware hashing (result #11)
 */
public class CFGComparator {

    public double compare(CFGNode cfg1, CFGNode cfg2) {
        if (cfg1 == null || cfg2 == null) return 0.0;

        // Extract all paths from both CFGs
        List<List<String>> paths1 = extractAllPaths(cfg1);
        List<List<String>> paths2 = extractAllPaths(cfg2);

        // Compare path signatures
        return comparePathSets(paths1, paths2);
    }

    private List<List<String>> extractAllPaths(CFGNode entryNode) {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<CFGNode> visited = new HashSet<>();

        // DFS to extract all paths from entry to exit
        extractPathsDFS(entryNode, currentPath, allPaths, visited);

        return allPaths;
    }

    private void extractPathsDFS(CFGNode node, List<String> currentPath,
                                 List<List<String>> allPaths, Set<CFGNode> visited) {

        // Add current node to path
        currentPath.add(node.getType());
        visited.add(node);

        // If it's an exit node, complete the path
        if (node.getSuccessors().isEmpty()) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            // Continue DFS on successors
            for (CFGNode successor : node.getSuccessors()) {
                // Avoid infinite loops by checking cycles
                if (!visited.contains(successor)) {
                    extractPathsDFS(successor, currentPath, allPaths, visited);
                }
            }
        }

        // Backtrack
        currentPath.remove(currentPath.size() - 1);
        visited.remove(node);
    }

    private double comparePathSets(List<List<String>> paths1, List<List<String>> paths2) {
        // Create path signatures
        Set<String> signatures1 = new HashSet<>();
        Set<String> signatures2 = new HashSet<>();

        for (List<String> path : paths1) {
            signatures1.add(String.join("->", path));
        }

        for (List<String> path : paths2) {
            signatures2.add(String.join("->", path));
        }

        // Calculate Jaccard similarity
        Set<String> union = new HashSet<>(signatures1);
        union.addAll(signatures2);

        Set<String> intersection = new HashSet<>(signatures1);
        intersection.retainAll(signatures2);

        return (double) intersection.size() / union.size();
    }
}
