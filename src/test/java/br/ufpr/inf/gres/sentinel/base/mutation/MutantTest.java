package br.ufpr.inf.gres.sentinel.base.mutation;

import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class MutantTest {

    public MutantTest() {
    }

    @Test
    public void isAlive() throws Exception {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        assertTrue(instance.isAlive());
        instance.getKillingTestCases().add(new TestCase("TestCase"));
        assertFalse(instance.isAlive());
        assertTrue(instance.isDead());
    }

    @Test
    public void testCloneConstructor() {
        Mutant instance = new Mutant("Program1", new File("Test"), IntegrationFacade.getProgramUnderTest());
        instance.getConstituentMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));
        instance.getConstituentMutants().add(new Mutant("Mutant3", null, IntegrationFacade.getProgramUnderTest()));
        instance.getOperators().add(new Operator("Operator1", "Type1"));
        instance.getOperators().add(new Operator("Operator2", "Type1"));
        Mutant instance2 = new Mutant(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getSourceFile(), instance2.getSourceFile());
        assertEquals(instance.getOriginalProgram(), instance2.getOriginalProgram());
        assertArrayEquals(instance.getConstituentMutants().toArray(), instance2.getConstituentMutants().toArray());
        assertArrayEquals(instance.getOperators().toArray(), instance2.getOperators().toArray());
    }

    @Test
    public void testEquals() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals2() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        assertEquals(instance, instance);
    }

    @Test
    public void testEquals3() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals4() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", ""));
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals5() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = null;
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals6() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Object instance2 = new Object();
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testGetAndSetConstituentMutants() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        SetUniqueList<Mutant> setUniqueList = SetUniqueList.setUniqueList(new ArrayList<>());
        instance.setConstituentMutants(setUniqueList);
        SetUniqueList result = instance.getConstituentMutants();
        assertEquals(setUniqueList, result);
    }

    @Test
    public void testGetAndSetEquivalent() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        instance.setEquivalent(true);
        boolean result = instance.isEquivalent();
        assertTrue(result);
    }

    @Test
    public void testGetAndSetKillingTestCases() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        SetUniqueList<TestCase> setUniqueList = SetUniqueList.setUniqueList(new ArrayList<>());
        instance.setKillingTestCases(setUniqueList);
        SetUniqueList result = instance.getKillingTestCases();
        assertEquals(setUniqueList, result);
    }

    @Test
    public void testGetAndSetName() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        instance.setName("MutantTest");
        String result = instance.getName();
        assertEquals("MutantTest", result);
    }

    @Test
    public void testGetAndSetOperators() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        SetUniqueList<Operator> setUniqueList = SetUniqueList.setUniqueList(new ArrayList<>());
        instance.setOperators(setUniqueList);
        SetUniqueList result = instance.getOperators();
        assertEquals(setUniqueList, result);
    }

    @Test
    public void testGetAndSetOriginalProgram() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        instance.setOriginalProgram(new Program("ProgramTest", ""));
        Program result = instance.getOriginalProgram();
        assertEquals(new Program("ProgramTest", ""), result);
    }

    @Test
    public void testHashCode() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", ""));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest());
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode4() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        Mutant instance2 = instance;
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testToString() {
        Mutant instance = new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest());
        assertEquals("Mutant1", instance.toString());
    }

}
