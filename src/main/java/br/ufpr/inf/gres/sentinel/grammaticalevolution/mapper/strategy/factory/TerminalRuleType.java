package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

/**
 *
 * @author Giovani Guizzo
 */
public class TerminalRuleType {

    // Empty
    public static final String EMPTY = "";

    // Strategy
    public static final String ALL_OPERATORS = "All Operators";

    // Default Operation
    public static final String NEW_BRANCH = "New Branch";
    public static final String STORE_MUTANTS = "Store Mutants";

    // Selection Type
    public static final String SEQUENTIAL = "Sequential";
    public static final String RANDOM = "Random";
    public static final String LAST_TO_FIRST = "LastToFirst";

    // Sorting Direction
    public static final String ASCENDING = "Ascending";
    public static final String DESCENDING = "Descending";

    // Operators Operation
    public static final String SELECT_OPERATORS = "Select Operators";
    public static final String SELECT_OPERATORS_BY_GROUPS = "Select Operators by Groups";

    public static final String DISCARD_OPERATORS = "Discard Operators";

    public static final String EXECUTE_OPERATORS = "Execute Operators";

    public static final String GROUP_OPERATORS = "Group Operators";

    public static final String TYPE = "Type";
    public static final String MUTANT_QUANTITY = "Mutant Quantity";

    public static final String MUTANT_QUANTITY_IN_GROUP = "Mutant Quantity in Group";
    public static final String OPERATOR_QUANTITY_IN_GROUP = "Operator Quantity in Group";

    public static final String CONVENTIONAL_EXECUTION = "Conventional Execution";

    private TerminalRuleType() {
    }

}
