package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Giovani Guizzo
 */
public class ConventionalGenerationTest {

    @BeforeClass
    public static void setUp() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
    }

    @Test
    public void doOperation() throws Exception {
        ConventionalGeneration operation = new ConventionalGeneration(2);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");

        Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        mutant1.getOperators().add(operator1);
        Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        mutant2.getOperators().add(operator2);
        Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());
        mutant3.getOperators().add(operator1);
        Mutant mutant4 = new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest());
        mutant4.getOperators().add(operator2);

        ArrayList<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4);
        List<Mutant> resultingHoms = operation.doOperation(mutants);

        assertEquals(2, resultingHoms.size());
        assertEquals("Mutant1_Mutant2", resultingHoms.get(0).getFullName());
        assertEquals("Mutant3_Mutant4", resultingHoms.get(1).getFullName());
    }

    @Test
    public void doOperation2() throws Exception {
        ConventionalGeneration operation = new ConventionalGeneration(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");

        Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        mutant1.getOperators().add(operator1);
        Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        mutant2.getOperators().add(operator2);
        Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());
        mutant3.getOperators().add(operator1);
        Mutant mutant4 = new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest());
        mutant4.getOperators().add(operator2);

        ArrayList<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4);
        List<Mutant> resultingHoms = operation.doOperation(mutants);

        assertEquals(1, resultingHoms.size());
        assertEquals("Mutant1_Mutant2_Mutant3", resultingHoms.get(0).getFullName());
    }

    @Test
    public void doOperation3() throws Exception {
        ConventionalGeneration operation = new ConventionalGeneration(3);

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");

        Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        mutant1.getOperators().add(operator1);
        Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        mutant2.getOperators().add(operator2);
        Mutant mutant3 = new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest());
        mutant3.getOperators().add(operator1);
        Mutant mutant4 = new Mutant("Mutant4", null, IntegrationFacade.getProgramUnderTest());
        mutant4.getOperators().add(operator2);
        Mutant mutant5 = new Mutant("Mutant5", null, IntegrationFacade.getProgramUnderTest());
        mutant4.getOperators().add(operator1);

        ArrayList<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4, mutant5);
        List<Mutant> resultingHoms = operation.doOperation(mutants);

        assertEquals(2, resultingHoms.size());
        assertEquals("Mutant1_Mutant2_Mutant3", resultingHoms.get(0).getFullName());
        assertEquals("Mutant4_Mutant5", resultingHoms.get(1).getFullName());
    }

    @Test
    public void getAndSetOrder() throws Exception {
        ConventionalGeneration operation = new ConventionalGeneration(2);
        operation.setOrder(3);
        assertEquals(3, operation.getOrder());
    }

    @Test
    public void isSpecific() throws Exception {
        ConventionalGeneration operation = new ConventionalGeneration(2);
        assertFalse(operation.isSpecific());
    }

}
