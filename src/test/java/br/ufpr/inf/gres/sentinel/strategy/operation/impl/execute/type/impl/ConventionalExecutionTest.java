package br.ufpr.inf.gres.sentinel.strategy.operation.impl.execute.type.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest.IntegrationFacadeStub;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class ConventionalExecutionTest {

    @BeforeClass
    public static void setUpClass() {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeStub());
    }

    public ConventionalExecutionTest() {
    }

    @Test
    public void testDoOperation() {
        ConventionalExecution operation = new ConventionalExecution();

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");
        Operator operator3 = new Operator("Operator3", "Type2");
        Operator operator4 = new Operator("Operator4", "Type3");
        List<Operator> operators = Lists.newArrayList(operator1, operator2, operator3, operator4);

        operation.doOperation(operators, new Program("Program1", "Program/path"));

        operators.forEach((operator) -> {
            assertEquals(4, operator.getGeneratedMutants().size());
        });
    }

    @Test
    public void testDoOperation2() {
        ConventionalExecution operation = new ConventionalExecution();

        List<Operator> operators = new ArrayList<>();

        operation.doOperation(operators, new Program("Program1", "Program/path"));
    }

}
