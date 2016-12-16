package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory;

/**
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

	// Operators
	public static final String SELECT_OPERATORS = "Select Operators";
	public static final String SELECT_OPERATORS_BY_GROUPS = "Select Operators by Groups";

	public static final String DISCARD_OPERATORS = "Discard Operators";

	public static final String EXECUTE_OPERATORS = "Execute Operators";

	public static final String TYPE = "Type";
	public static final String MUTANT_QUANTITY = "Mutant Quantity";

	public static final String MUTANT_QUANTITY_IN_GROUP = "Mutant Quantity in Group";

	public static final String CONVENTIONAL = "Conventional";

	// Mutants
	public static final String SELECT_MUTANTS = "Select Mutants";
	public static final String DISCARD_MUTANTS = "Discard Mutants";
	public static final String COMBINE_MUTANTS = "Combine Mutants";
	public static final String SELECT_MUTANTS_BY_GROUPS = "Select Mutants by Groups";
	public static final String SINGLE_HOM = "Single HOM";

	public static final String OPERATOR_TYPE = "Operator Type";
	public static final String OPERATOR = "Operator";
	public static final String FOM_OR_HOM = "FOM or HOM";
	public static final String ORDER = "Order";
	public static final String QUANTITY_IN_GROUP = "Quantity in Group";

	private TerminalRuleType() {
	}

}
