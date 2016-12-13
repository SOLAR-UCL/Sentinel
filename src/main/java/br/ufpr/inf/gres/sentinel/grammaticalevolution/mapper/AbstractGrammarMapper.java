package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import com.google.common.base.CharMatcher;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for interpreting a grammar file into a structure of nodes for later parsing a vector of integers into a type T.
 *
 * @param <T> Type of result from parsing a vector
 *
 * @author Giovani Guizzo
 */
public abstract class AbstractGrammarMapper<T> {

    /**
     * Root node. The first node representing the result.
     */
    protected Rule rootNode;

    /**
     * List of all non-terminal nodes found.
     */
    protected HashMap<String, Rule> nonTerminalNodes;

    /**
     * List of all terminal nodes found.
     */
    protected HashMap<String, Rule> terminalNodes;

    /**
     * @param grammarFilePath The path of the grammar file.
     *
     * @throws IOException If any problem occurs during the grammar reading.
     */
    public AbstractGrammarMapper(String grammarFilePath) throws IOException {
        this();
        loadGrammar(grammarFilePath);
    }

    /**
     * @param grammarFile The grammar file.
     *
     * @throws IOException If any problem occurs during the grammar reading.
     */
    public AbstractGrammarMapper(File grammarFile) throws IOException {
        this();
        loadGrammar(grammarFile);
    }

    public AbstractGrammarMapper() {
        initialize();
    }

    /**
     * Initializes the class, erasing all nodes.
     */
    private void initialize() {
        this.nonTerminalNodes = new HashMap<>();
        this.terminalNodes = new HashMap<>();
        this.rootNode = null;
    }

    public Rule getRootNode() {
        return rootNode;
    }

    /**
     * Get or create a new rule object.
     *
     * @param ruleName The rule's name.
     *
     * @return The rule object.
     */
    public Rule getNonTerminalRule(String ruleName) {
        return nonTerminalNodes.computeIfAbsent(ruleName, Rule::new);
    }

    /**
     * Get or create a new rule object.
     *
     * @param ruleName The rule's name.
     *
     * @return The rule object.
     */
    public Rule getTerminalRule(String ruleName) {
        return terminalNodes.computeIfAbsent(ruleName, Rule::new);
    }

    /**
     * Loads a grammar in memory, interpreting it into an Rule-Options structure.
     *
     * @param grammarFilePath The grammar file's path.
     *
     * @return If the grammar was successfully loaded.
     *
     * @throws IOException If the file is not found, corrupted, or contains an erroneous grammar syntax.
     */
    public final boolean loadGrammar(String grammarFilePath) throws IOException {
        return loadGrammar(new File(grammarFilePath));
    }

    /**
     * Loads a grammar in memory, interpreting it into an Rule-Options structure.
     *
     * @param grammarFile The grammar file.
     *
     * @return If the grammar was successfully loaded.
     *
     * @throws IOException If the file is not found, corrupted, or contains an erroneous grammar syntax.
     */
    public final boolean loadGrammar(File grammarFile) throws IOException {

        try {
            // Initialize nodes
            initialize();
            // Read grammar file
            List<String> lines = Files.readLines(grammarFile, Charset.defaultCharset());
            // Start interpreting the file
            for (String line : lines) {
                line = line.trim();
                // If it is a grammar line
                if (!line.startsWith("#") && line.contains("::=")) {
                    // Ignore any comment after the rule and expression
                    line = Splitter.on("#").trimResults().splitToList(line).get(0);
                    // Split into rule and expressions
                    List<String> lineSplit = Splitter.on("::=").trimResults().splitToList(line);
                    // Get rule name
                    String ruleName = lineSplit.get(0);
                    ruleName = CharMatcher.anyOf("<>").removeFrom(ruleName);
                    if (!ruleName.isEmpty()) {
                        // Get or create the node of this rule
                        Rule rule = getNonTerminalRule(ruleName);
                        // Update the root node if it is null
                        rootNode = MoreObjects.firstNonNull(rootNode, rule);
                        // Get the whole expression containing all options
                        String fullExpression = lineSplit.get(1);
                        // Split into expression options
                        List<String> options = Splitter.on("|").trimResults().omitEmptyStrings().splitToList(fullExpression);
                        // Iterate over all options building an option object for each
                        for (int optionIndex = 0; optionIndex < options.size(); optionIndex++) {
                            String optionString = options.get(optionIndex);
                            Option option = buildOption(optionString);
                            rule.addOption(option);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new IOException("File is not a file or does not exist.", ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("The grammar file contains a syntax error.", ex);
        }
        if (rootNode == null) {
            throw new IllegalArgumentException("I could not find any grammar in the file.");
        }
        return true;
    }

    /**
     * A regex to match BNF options. Group 1 is non-terminal, Group 2 is terminal.
     */
    private static final Pattern BNF_OPTION_PATTERN = Pattern.compile("((?<=<).+?(?=>))|((?<=\")(?:[\\S]+?.*?|)(?=\")|\\(|\\))");
    private static final int NON_TERMINAL_RULE_GROUP = 1;
    private static final int TERMINAL_RULE_GROUP = 2;

    /**
     * A method to build an option based on an option string.
     *
     * @param optionString The option string, e.g., 'op expr op'.
     *
     * @return An option object.
     *
     * @throws IOException If there is any syntax error in the grammar file.
     */
    private Option buildOption(String optionString) throws IOException {
        // Create a new option
        Option option = new Option();
        Matcher matcher = BNF_OPTION_PATTERN.matcher(optionString);
        // Match a rule in an onption.
        while (matcher.find()) {
            String foundRule;
            // Discover rule's group.
            int ruleGroup = 0;
            do {
                foundRule = matcher.group(++ruleGroup);
            } while (foundRule == null);
            // Get rule based on its type.
            Rule rule = null;
            switch (ruleGroup) {
                case NON_TERMINAL_RULE_GROUP:
                    rule = getNonTerminalRule(foundRule);
                    break;
                case TERMINAL_RULE_GROUP:
                    rule = getTerminalRule(foundRule);
                    break;
            }
            // Add rule to the option.
            option.addRule(rule);
        }
        // Invalid option with no rules.
        if (option.getRules().isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("No rules for option.");
        }
        return option;
    }

    /**
     * Interprets an integer vector using the loaded grammar.
     *
     * @param integerIterable The integer vector.
     *
     * @return The built object.
     */
    public T interpret(Iterable<Integer> integerIterable) {
        //TODO set maximum wraps
        return hookInterpret(Iterables.unmodifiableIterable(Iterables.cycle(integerIterable)).iterator());
    }

    /**
     * When interpreting an integer vector, this is what the method {@link AbstractGrammarMapper#interpret(java.lang.Iterable)
     * interpret} will call.
     *
     * @param cyclicIterator The unmodifiable integer vector in form of a cyclic iterator object.
     *
     * @return The built object.
     */
    protected abstract T hookInterpret(Iterator<Integer> cyclicIterator);

}
