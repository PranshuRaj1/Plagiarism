package PlagiarismDetector.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a node in a Control Flow Graph (CFG).
 * Each node corresponds to a statement or block in the code.
 */
public class CFGNode {
    private String id;
    private String type;
    private List<CFGNode> successors;
    private List<CFGEdge> outgoingEdges;

    /**
     * Creates a new CFG node with the specified type.
     *
     * @param type The type of the node (e.g., "IF", "LOOP", "STATEMENT")
     */
    public CFGNode(String type) {
        this.id = "node_" + System.nanoTime();
        this.type = type;
        this.successors = new ArrayList<>();
        this.outgoingEdges = new ArrayList<>();
    }

    /**
     * Gets the type of this node.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the unique ID of this node.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the list of successor nodes in the CFG.
     */
    public List<CFGNode> getSuccessors() {
        return new ArrayList<>(successors);
    }

    /**
     * Gets the list of outgoing edges from this node.
     */
    public List<CFGEdge> getOutgoingEdges() {
        return new ArrayList<>(outgoingEdges);
    }

    /**
     * Adds a successor node with a default edge.
     *
     * @param successor The successor node to add
     */
    public void addSuccessor(CFGNode successor) {
        if (successor != null && !successors.contains(successor)) {
            successors.add(successor);
            CFGEdge edge = new CFGEdge(this, successor);
            outgoingEdges.add(edge);
        }
    }

    /**
     * Adds a successor node with a labeled edge.
     *
     * @param successor The successor node to add
     * @param edgeLabel The label for the edge (e.g., "true", "false" for conditionals)
     */
    public void addSuccessor(CFGNode successor, String edgeLabel) {
        if (successor != null && !successors.contains(successor)) {
            successors.add(successor);
            CFGEdge edge = new CFGEdge(this, successor, edgeLabel);
            outgoingEdges.add(edge);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CFGNode cfgNode = (CFGNode) o;
        return Objects.equals(id, cfgNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
