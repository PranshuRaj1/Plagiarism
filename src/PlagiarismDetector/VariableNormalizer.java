package PlagiarismDetector;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VariableNormalizer {
    /**
     * Normalizes all variable names in the code to generic names (e.g., var1, var2, ...).
     * This example uses a simple regex and assumes variable names match the pattern.
     */
    public static String normalizeVariables(String code) {
        Map<String, String> varMap = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(1);
        Pattern pattern = Pattern.compile("\\b([a-zA-Z_$][\\w$]*)\\b");
        Matcher matcher = pattern.matcher(code);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String varName = matcher.group(1);
            // You might want to filter out keywords by checking with LexerUtilities.isKeyword(varName)
            if (!LexerUtilities.isKeyword(varName)) {
                String replacement = varMap.computeIfAbsent(varName, k -> "var" + counter.getAndIncrement());
                matcher.appendReplacement(sb, replacement);
            } else {
                matcher.appendReplacement(sb, varName);
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
