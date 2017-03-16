package br.ufpr.inf.gres.sentinel.integration.pitest;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class PitestFacade extends IntegrationFacade {

    @Override
    public List<Operator> getAllOperators() {
        List<Operator> allOperators = new ArrayList<>();
        allOperators.add(new Operator("CONDITIONALS_BOUNDARY", "Conditionals"));
        allOperators.add(new Operator("NEGATE_CONDITIONALS", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_EQ_IF", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_EQ_ELSE", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_ORD_IF", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_ORD_ELSE", "Conditionals"));
        allOperators.add(new Operator("MATH", "Variables"));
        allOperators.add(new Operator("INCREMENTS", "Variables"));
        allOperators.add(new Operator("INVERT_NEGS", "Variables"));
        allOperators.add(new Operator("INLINE_CONSTS", "Variables"));
        allOperators.add(new Operator("EXPERIMENTAL_MEMBER_VARIABLE", "Variables"));
        allOperators.add(new Operator("RETURN_VALS", "Method"));
        allOperators.add(new Operator("VOID_METHOD_CALLS", "Method"));
        allOperators.add(new Operator("NON_VOID_METHOD_CALLS", "Method"));
        allOperators.add(new Operator("CONSTRUCTOR_CALLS", "Method"));
        allOperators.add(new Operator("EXPERIMENTAL_SWITCH", "Method"));
        allOperators.add(new Operator("EXPERIMENTAL_ARGUMENT_PROPAGATION", "Method"));
        return allOperators;
    }

    @Override
    public List<Mutant> executeOperator(Operator operator) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public List<Mutant> executeOperators(List<Operator> operators) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public void executeMutant(Mutant mutantToExecute) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public void executeMutants(List<Mutant> mutantsToExecute) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public Mutant combineMutants(List<Mutant> mutantsToCombine) {
        throw new UnsupportedOperationException("Sorry, PIT test is not adapted to work with HOMs.");
    }

}
