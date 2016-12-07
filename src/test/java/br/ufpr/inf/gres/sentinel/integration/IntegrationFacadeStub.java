package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class IntegrationFacadeStub extends IntegrationFacade {

    public IntegrationFacadeStub() {
    }

    public List<Operator> getAllOperators() {
        return Lists.newArrayList(
                new Operator("Operator1", "Type1"),
                new Operator("Operator2", "Type1"),
                new Operator("Operator3", "Type2"),
                new Operator("Operator4", "Type3")
        );
    }

    public List<Mutant> executeOperator(Operator operator, Program programToBeMutated) {
        return Lists.newArrayList(
                new Mutant(operator + "1", new File(operator + "1"), programToBeMutated),
                new Mutant(operator + "2", new File(operator + "2"), programToBeMutated),
                new Mutant(operator + "3", new File(operator + "3"), programToBeMutated),
                new Mutant(operator + "4", new File(operator + "4"), programToBeMutated)
        );
    }

}
