package PlagiarismDetector.ast.parser;

import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.core.dom.parser.*;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.model.ILanguage;

import PlagiarismDetector.ast.model.NodeType;
import PlagiarismDetector.ast.model.UniversalASTNode;

import java.util.HashMap;

/**
 * Parser for C++ source code using Eclipse CDT
 * Based on search results #1 and #13
 */
public class CppASTParser implements ASTParser {

    @Override
    public UniversalASTNode parse(String sourceCode) {
        try {
            // Configure the parser
            ILanguage language = GPPLanguage.getDefault();
            HashMap<String, String> definedSymbols = new HashMap<>();
            String[] includePaths = new String[0];
            IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
            IParserLogService log = new DefaultLogService();

            // Create file content from the source string
            FileContent content = FileContent.create("<input>", sourceCode.toCharArray());

            // Parse the file
            IASTTranslationUnit translationUnit = language.getASTTranslationUnit(
                    content, info, IncludeFileContentProvider.getEmptyFilesProvider(), null,
                    ILanguage.OPTION_IS_SOURCE_UNIT, log);

            // Convert to universal AST
            return convertToUniversalAST(translationUnit);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse C++ code", e);
        }
    }

    private UniversalASTNode convertToUniversalAST(IASTNode node) {
        // Map CDT node types to our universal types
        NodeType type = mapNodeType(node);
        UniversalASTNode uNode = new UniversalASTNode(type, node.getRawSignature());

        // Add relevant attributes
        if (node.getFileLocation() != null) {
            uNode.addAttribute("startLine",
                    String.valueOf(node.getFileLocation().getStartingLineNumber()));
            uNode.addAttribute("endLine",
                    String.valueOf(node.getFileLocation().getEndingLineNumber()));
        }

        // Process children recursively
        for (IASTNode child : node.getChildren()) {
            uNode.addChild(convertToUniversalAST(child));
        }

        return uNode;
    }

    private NodeType mapNodeType(IASTNode node) {
        // Map C++-specific node types to universal types
        if (node instanceof IASTFunctionDefinition) return NodeType.METHOD_DECLARATION;
        if (node instanceof IASTCompoundStatement) return NodeType.BLOCK;
        if (node instanceof IASTIfStatement) return NodeType.IF_STATEMENT;
        if (node instanceof IASTForStatement) return NodeType.FOR_LOOP;
        if (node instanceof IASTWhileStatement) return NodeType.WHILE_LOOP;
        if (node instanceof IASTReturnStatement) return NodeType.RETURN_STATEMENT;
        if (node instanceof IASTBinaryExpression) return NodeType.BINARY_EXPRESSION;
        if (node instanceof IASTDeclarationStatement) return NodeType.VARIABLE_DECLARATION;

        return NodeType.UNKNOWN;
    }
}
