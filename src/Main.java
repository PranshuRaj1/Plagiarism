import PlagiarismDetector.PlagiarismReport;
import PlagiarismDetector.WinnowingConfig;
import PlagiarismDetector.WinnowingDetector;

public class Main {
    // first step is to strip out  comments, whitespace, and normalizing tokens (here done in a rudimentary way).
    // implement Lexer

    public static void main(String[] args) {
        // Example usage with test sources
        String source1 = "public class BinarySearchExample {\n" +
                "    // Returns the index of target in arr if present, otherwise returns -1\n" +
                "    public static int binarySearch(int[] arr, int target) {\n" +
                "        int left = 0;\n" +
                "        int right = arr.length - 1;\n" +
                "        \n" +
                "        while (left <= right) {\n" +
                "            int mid = left + (right - left) / 2;\n" +
                "            \n" +
                "            // Check if the target is present at mid\n" +
                "            if (arr[mid] == target) {\n" +
                "                return mid;\n" +
                "            }\n" +
                "            // If target is greater, ignore left half\n" +
                "            else if (arr[mid] < target) {\n" +
                "                left = mid + 1;\n" +
                "            }\n" +
                "            // If target is smaller, ignore right half\n" +
                "            else {\n" +
                "                right = mid - 1;\n" +
                "            }\n" +
                "        }\n" +
                "        // Target was not found in the array\n" +
                "        return -1;\n" +
                "    }\n" +
                "    \n" +
                "    public static void main(String[] args) {\n" +
                "        int[] numbers = {2, 4, 6, 8, 10, 12, 14, 16, 18};\n" +
                "        int target = 10;\n" +
                "        int index = binarySearch(numbers, target);\n" +
                "        \n" +
                "        if (index != -1) {\n" +
                "            System.out.println(\"Element \" + target + \" found at index \" + index);\n" +
                "        } else {\n" +
                "            System.out.println(\"Element \" + target + \" not found in the array.\");\n" +
                "        }\n" +
                "    }\n" +
                "}";

        String source2 = "/**\n" +
                " * Implementation of binary search algorithm in C++\n" +
                " */\n" +
                "#include <iostream>\n" +
                "#include <vector>\n" +
                "\n" +
                "/**\n" +
                " * Locates a specific value within a sorted array using binary search technique\n" +
                " * @param sortedArray The ordered integer array to search within\n" +
                " * @param searchValue The integer value to locate\n" +
                " * @return The position of the value if found, or -1 if not present\n" +
                " */\n" +
                "int findValuePosition(const std::vector<int>& sortedArray, int searchValue) {\n" +
                "    int lowerBound = 0;\n" +
                "    int upperBound = sortedArray.size() - 1;\n" +
                "    \n" +
                "    while (lowerBound <= upperBound) {\n" +
                "        // Calculate midpoint using technique that prevents integer overflow\n" +
                "        int midpoint = lowerBound + (upperBound - lowerBound) / 2;\n" +
                "        \n" +
                "        // Target value found at midpoint\n" +
                "        if (sortedArray[midpoint] == searchValue) {\n" +
                "            return midpoint;\n" +
                "        }\n" +
                "        \n" +
                "        // Adjust search boundaries based on comparison\n" +
                "        if (sortedArray[midpoint] < searchValue) {\n" +
                "            // Search the upper half\n" +
                "            lowerBound = midpoint + 1;\n" +
                "        } else {\n" +
                "            // Search the lower half\n" +
                "            upperBound = midpoint - 1;\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    // Indicate value was not found in array\n" +
                "    return -1;\n" +
                "}\n" +
                "\n" +
                "int main() {\n" +
                "    // Sample dataset for demonstration\n" +
                "    std::vector<int> sequence = {2, 4, 6, 8, 10, 12, 14, 16, 18};\n" +
                "    int desiredValue = 10;\n" +
                "    \n" +
                "    // Execute the search\n" +
                "    int resultPosition = findValuePosition(sequence, desiredValue);\n" +
                "    \n" +
                "    // Display results based on search outcome\n" +
                "    if (resultPosition >= 0) {\n" +
                "        std::cout << \"Value \" << desiredValue << \" located at position \" << resultPosition << std::endl;\n" +
                "    } else {\n" +
                "        std::cout << \"Value \" << desiredValue << \" does not exist in the sequence.\" << std::endl;\n" +
                "    }\n" +
                "    \n" +
                "    return 0;\n" +
                "}";

        // Configure detector with recommended parameters for code comparison
        WinnowingConfig config = new WinnowingConfig.Builder()
                .setKGramSize(5)
                .setWindowSize(6)
                .setNormalizeIdentifiers(true)
                .setIgnoreComments(true)
                .build();

        WinnowingDetector detector = new WinnowingDetector(config);

        // Run basic comparison
        double similarity = detector.compareCode(source1, source2);
        System.out.println("Similarity: " + similarity + "%");

        // Run detailed analysis
        PlagiarismReport report = detector.analyzeCode(source1, source2);
        System.out.println(report);
    }
}