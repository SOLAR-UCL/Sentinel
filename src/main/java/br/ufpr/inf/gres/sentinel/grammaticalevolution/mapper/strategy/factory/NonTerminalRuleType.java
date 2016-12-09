package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

/**
 *
 * @author Giovani Guizzo
 */
public class NonTerminalRuleType {

    // Default Rules
    public static final String UNKNOWN_NON_TERMINAL = "non-terminal";
    public static final String STRATEGY = "strategy";
    public static final String DEFAULT_OPERATION = "defaultOperation";
    public static final String QUANTITY = "quantity";
    public static final String PERCENTAGE = "percentage";
    public static final String SELECTION_TYPE = "selectionType";
    public static final String SORTING_DIRECTION = "sortingDirection";

    // Operator Rules
    public static final String OPERATOR_OPERATION = "operatorOperation";
    public static final String SELECT_OPERATORS = "selectOperators";
    public static final String OPERATOR_SELECTION_TYPE = "operatorSelectionType";
    public static final String OPERATOR_SORTING = "operatorSorting";
    public static final String SELECT_OPERATOR_GROUPS = "selectOperatorGroups";
    public static final String OPERATOR_GROUP_SELECTION_TYPE = "operatorGroupSelectionType";
    public static final String OPERATOR_GROUPING = "operatorGrouping";
    public static final String OPERATOR_GROUP_SORTING = "operatorGroupSorting";
    public static final String OPERATOR_ATTRIBUTE = "operatorAttribute";
    public static final String OPERATOR_GROUP_ATTRIBUTE = "operatorGroupAttribute";
    public static final String OPERATOR_EXECUTION_TYPE = "operatorExecutionType";

    // Mutant Rules
    public static final String MUTANT_OPERATION = "mutantOperation";
    public static final String SELECT_MUTANTS = "selectMutants";
    public static final String MUTANT_SELECTION_TYPE = "mutantSelectionType";
    public static final String MUTANT_SORTING = "mutantSorting";
    public static final String SELECT_MUTANT_GROUPS = "selectMutantGroups";
    public static final String MUTANT_GROUP_SELECTION_TYPE = "mutantGroupSelectionType";
    public static final String MUTANT_GROUPING = "mutantGrouping";
    public static final String MUTANT_GROUP_SORTING = "mutantGroupSorting";
    public static final String HOM_GENERATION = "HOMGeneration";
    public static final String ORDER = "order";
    public static final String MUTANT_ATTRIBUTE = "mutantAttribute";
    public static final String MUTANT_GROUP_ATTRIBUTE = "mutantGroupAttribute";

    private NonTerminalRuleType() {
    }

}
