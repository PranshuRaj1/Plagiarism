package PlagiarismDetector.cfg;

import java.util.Objects;

/**
 * Represents an edge in a Control Flow Graph (CFG).
 * Connects two CFG nodes and may have a label describing the flow condition.
 */
public class CFGEdge {
    private CFGNode source;
    private CFGNode target;
    private String label;

    /**
     * Creates a new edge between source and target nodes.
     *
     * @param source The source node
     * @param target The target node
     */
    public CFGEdge(CFGNode source, CFGNode target) {
        this.source = source;
        this.target = target;
        this.label = "";
    }

    /**
     * Creates a new labeled edge between source and target nodes.
     *
     * @param source The source node
     * @param target The target node
     * @param label The edge label (e.g., "true" for a conditional branch)
     */
    public CFGEdge(CFGNode source, CFGNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    /**
     * Gets the source node of this edge.
     */
    public CFGNode getSource() {
        return source;
    }

    /**
     * Gets the target node of this edge.
     */
    public CFGNode getTarget() {
        return target;
    }

    /**
     * Gets the label of this edge.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this edge.
     *
     * @param label The new label
     */
    public void setLabel(String label) {
        this.label = label != null ? label : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CFGEdge cfgEdge = (CFGEdge) o;
        return Objects.equals(source, cfgEdge.source) &&
                Objects.equals(target, cfgEdge.target) &&
                Objects.equals(label, cfgEdge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, label);
    }
}
