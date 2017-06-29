package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.LinkedHashSet;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class MutantTest {

    public MutantTest() {
    }

    @Test
    public void isAlive() throws Exception {
        Mutant instance = new Mutant("Mutant1", null, null);
        assertTrue(instance.isAlive());
        instance.getKillingTestCases().add(new TestCase("TestCase"));
        assertFalse(instance.isAlive());
        assertTrue(instance.isDead());
    }

    @Test
    public void testCloneConstructor() {
        Mutant instance = new Mutant("Program1", new File("Test"), new Program("Program1", "Program/path"));
        instance.setOperator(new Operator("Operator1", "Type1"));
        Mutant instance2 = new Mutant(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getSourceFile(), instance2.getSourceFile());
        assertEquals(instance.getOriginalProgram(), instance2.getOriginalProgram());
        assertEquals(instance.getOperator(), instance2.getOperator());
    }

    @Test
    public void testEquals() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals2() {
        Mutant instance = new Mutant("Mutant1", null, null);
        assertEquals(instance, instance);
    }

    @Test
    public void testEquals3() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals4() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", ""));
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals5() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = null;
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals6() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Object instance2 = new Object();
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testGetAndSetEquivalent() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        instance.setEquivalent(true);
        boolean result = instance.isEquivalent();
        assertTrue(result);
    }

    @Test
    public void testGetAndSetKillingTestCases() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        LinkedHashSet<TestCase> setUniqueList = new LinkedHashSet<>();
        instance.setKillingTestCases(setUniqueList);
        LinkedHashSet<TestCase> result = instance.getKillingTestCases();
        assertEquals(setUniqueList, result);
    }

    @Test
    public void testGetAndSetName() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        instance.setName("MutantTest");
        String result = instance.getName();
        assertEquals("MutantTest", result);
    }

    @Test
    public void testGetAndSetOperator() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Operator operator = new Operator("Operator1", "Type1");
        instance.setOperator(operator);
        Operator result = instance.getOperator();
        assertEquals(operator, result);
    }

    @Test
    public void testGetAndSetOriginalProgram() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        instance.setOriginalProgram(new Program("ProgramTest", ""));
        Program result = instance.getOriginalProgram();
        assertEquals(new Program("ProgramTest", ""), result);
    }

    @Test
    public void testHashCode() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", ""));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = new Mutant("Mutant2", null, new Program("Program1", "Program/path"));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode4() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        Mutant instance2 = instance;
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testToString() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", "Program/path"));
        assertEquals("Mutant1", instance.toString());
    }

}
