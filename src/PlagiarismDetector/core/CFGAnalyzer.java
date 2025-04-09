package PlagiarismDetector.core;

import PlagiarismDetector.ast.model.UniversalASTNode;
import PlagiarismDetector.ast.parser.JavaASTParser;
import PlagiarismDetector.ast.parser.CppASTParser;
import PlagiarismDetector.ast.visitor.NormalizationVisitor;
import PlagiarismDetector.cfg.CFGBuilder;
import PlagiarismDetector.cfg.CFGNode;
import PlagiarismDetector.similarity.CFGComparator;
import PlagiarismDetector.similarity.ASTComparator;
import PlagiarismDetector.util.LanguageDetector;

/**
 * Enhanced CFG analyzer that compares code using both AST and CFG techniques
 */
public class CFGAnalyzer {
    private final JavaASTParser javaParser = new JavaASTParser();
    private final CppASTParser cppParser = new CppASTParser();
    private final NormalizationVisitor normalizer = new NormalizationVisitor();
    private final CFGBuilder cfgBuilder = new CFGBuilder();
    private final CFGComparator cfgComparator = new CFGComparator();
    private final ASTComparator astComparator = new ASTComparator();

    /**
     * Compares two code snippets using a hybrid of AST and CFG analysis
     * Returns a similarity score between 0.0 and 1.0
     */
    public double compareCode(String code1, String code2) {
        // Parse code to ASTs
        UniversalASTNode ast1 = parseToAST(code1);
        UniversalASTNode ast2 = parseToAST(code2);

        // Normalize ASTs to handle variable renaming and code restructuring
        UniversalASTNode normalizedAst1 = normalizer.normalize(ast1);
        UniversalASTNode normalizedAst2 = normalizer.normalize(ast2);

        // Calculate AST similarity
        double astSimilarity = astComparator.compare(normalizedAst1, normalizedAst2);

        // Build and compare CFGs
        CFGNode cfg1 = cfgBuilder.build(normalizedAst1);
        CFGNode cfg2 = cfgBuilder.build(normalizedAst2);
        double cfgSimilarity = cfgComparator.compare(cfg1, cfg2);

        // Combine scores with more weight on CFG similarity
        // The 0.4/0.6 weighting is based on empirical results from Paper #10
        return (0.4 * astSimilarity) + (0.6 * cfgSimilarity);
    }

    /**
     * Parses code to a universal AST using the appropriate language parser
     */
    private UniversalASTNode parseToAST(String code) {
        String language = LanguageDetector.detectLanguage(code);

        // Select the appropriate parser based on language
        if ("java".equalsIgnoreCase(language)) {
            return javaParser.parse(code);
        } else if ("cpp".equalsIgnoreCase(language)) {
            return cppParser.parse(code);
        } else {
            // Default to Java if language can't be determined
            return javaParser.parse(code);
        }
    }
}
