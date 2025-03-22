package PlagiarismDetector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Detects matching sections between two documents.
 */
public class MatchDetector {
    /**
     * Find matching sections between two k-gram sequences.
     */
    public static List<MatchedSection> findMatchingSections(List<String> kgrams1, List<String> kgrams2) {
        List<MatchedSection> matchedSections = new ArrayList<>();
        Map<String, List<Integer>> kgramPositions = indexKGrams(kgrams2);
        for (int i = 0; i < kgrams1.size(); i++) {
            String kgram = kgrams1.get(i);
            if (kgramPositions.containsKey(kgram)) {
                for (int startPos2 : kgramPositions.get(kgram)) {
                    MatchedSection match = extendMatch(kgrams1, kgrams2, i, startPos2);
                    if (match.getLength() > 1) {
                        matchedSections.add(match);
                    }
                }
            }
        }
        return mergeOverlappingSections(matchedSections);
    }

    private static Map<String, List<Integer>> indexKGrams(List<String> kgrams) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < kgrams.size(); i++) {
            String kgram = kgrams.get(i);
            map.computeIfAbsent(kgram, k -> new ArrayList<>()).add(i);
        }
        return map;
    }

    private static MatchedSection extendMatch(List<String> kgrams1, List<String> kgrams2, int pos1, int pos2) {
        int startPos1 = pos1;
        int startPos2 = pos2;
        while (pos1 < kgrams1.size() && pos2 < kgrams2.size() &&
                kgrams1.get(pos1).equals(kgrams2.get(pos2))) {
            pos1++;
            pos2++;
        }
        int length = pos1 - startPos1;
        return new MatchedSection(startPos1, startPos2, length);
    }

    private static List<MatchedSection> mergeOverlappingSections(List<MatchedSection> sections) {
        if (sections.isEmpty()) return sections;
        sections.sort(Comparator.comparingInt(MatchedSection::getPos1));
        List<MatchedSection> merged = new ArrayList<>();
        MatchedSection current = sections.get(0);
        for (int i = 1; i < sections.size(); i++) {
            MatchedSection next = sections.get(i);
            if (current.getPos1() + current.getLength() >= next.getPos1()) {
                int newLength = Math.max(current.getLength(), next.getPos1() - current.getPos1() + next.getLength());
                current = new MatchedSection(current.getPos1(), current.getPos2(), newLength);
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);
        return merged;
    }
}
