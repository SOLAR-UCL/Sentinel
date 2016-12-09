package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantTest {

    public MutantTest() {
    }

    @Test
    public void testHashCode() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program1", null));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", null));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant2", null, new Program("Program1", null));
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode4() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = instance;
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testEquals() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program1", null));
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals2() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        assertEquals(instance, instance);
    }

    @Test
    public void testEquals3() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant2", null, new Program("Program1", null));
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals4() {
        Mutant instance = new Mutant("Mutant1", null, new Program("Program1", null));
        Mutant instance2 = new Mutant("Mutant1", null, new Program("Program2", null));
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testCloneConstructor() {
        Mutant instance = new Mutant("Program1", new File("Test"), new Program("Program1", new File("ProgramTest")));
        instance.getConstituentMutants().add(new Mutant("Mutant2", null, new Program("Program1", null)));
        instance.getConstituentMutants().add(new Mutant("Mutant3", null, new Program("Program1", null)));
        instance.getOperators().add(new Operator("Operator1", "Type1"));
        instance.getOperators().add(new Operator("Operator2", "Type1"));
        Mutant instance2 = new Mutant(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getSourceFile(), instance2.getSourceFile());
        assertEquals(instance.getOriginalProgram(), instance2.getOriginalProgram());
        assertArrayEquals(instance.getConstituentMutants().toArray(), instance2.getConstituentMutants().toArray());
        assertArrayEquals(instance.getOperators().toArray(), instance2.getOperators().toArray());
    }

}
