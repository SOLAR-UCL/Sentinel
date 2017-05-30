package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacadeTest;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class SingleHOMGenerationTest {

    @BeforeClass
    public static void setUp() throws Exception {
        IntegrationFacade.setIntegrationFacade(new IntegrationFacadeTest.IntegrationFacadeStub());
    }

    @Test
    public void doOperation() throws Exception {
        SingleHOMGeneration operation = new SingleHOMGeneration();

        Operator operator1 = new Operator("Operator1", "Type1");
        Operator operator2 = new Operator("Operator2", "Type1");

        Mutant mutant1 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        mutant1.getOperators().add(operator1);
        Mutant mutant2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        mutant2.getOperators().add(operator2);

        List<Mutant> mutants = operation.doOperation(Lists.newArrayList(mutant1, mutant2));
        assertFalse(mutants.isEmpty());
        assertEquals(1, mutants.size());

        Mutant hom = mutants.get(0);

        assertNotNull(hom);
        assertEquals("Mutant1_Mutant2", hom.getName());
        assertArrayEquals(new Mutant[]{mutant1, mutant2}, hom.getConstituentMutants().toArray());
        assertArrayEquals(new Operator[]{operator1, operator2}, hom.getOperators().toArray());
        assertEquals(2, hom.getOrder());
        assertTrue(hom.isHigherOrder());
        assertTrue(operator1.getGeneratedMutants().contains(hom));
        assertTrue(operator2.getGeneratedMutants().contains(hom));

        mutants = operation.doOperation(Lists.newArrayList(mutant1));
        assertTrue(mutants.isEmpty());
    }

    @Test
    public void isSpecific() throws Exception {
        SingleHOMGeneration operation = new SingleHOMGeneration();
        assertFalse(operation.isSpecific());
    }

}
