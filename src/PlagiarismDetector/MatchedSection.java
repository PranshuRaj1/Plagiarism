package PlagiarismDetector;

/**
 * Represents a matching section between two documents.
 */
public class MatchedSection {
    private final int pos1;
    private final int pos2;
    private final int length;

    public MatchedSection(int pos1, int pos2, int length) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.length = length;
    }

    public int getPos1() {
        return pos1;
    }

    public int getPos2() {
        return pos2;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Match at position " + pos1 + " in source1, position " + pos2 + " in source2, length " + length;
    }
}
