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

    @Override
    public List<Operator> getAllOperators() {
        return Lists.newArrayList(
                new Operator("Operator1", "Type1"),
                new Operator("Operator2", "Type1"),
                new Operator("Operator3", "Type2"),
                new Operator("Operator4", "Type3")
        );
    }

    @Override
    public List<Mutant> executeOperator(Operator operator, Program programToBeMutated) {
        return Lists.newArrayList(
                new Mutant(operator + "_1", new File(operator + "_1"), programToBeMutated),
                new Mutant(operator + "_2", new File(operator + "_2"), programToBeMutated),
                new Mutant(operator + "_3", new File(operator + "_3"), programToBeMutated),
                new Mutant(operator + "_4", new File(operator + "_4"), programToBeMutated)
        );
    }

}
