package br.ufpr.inf.gres.sentinel.base.mutation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OperatorTest {

    public OperatorTest() {
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator1", "Type1");
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        System.out.println("hashCode2");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator2", "Type1");
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        System.out.println("hashCode3");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = instance;
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode4() {
        System.out.println("hashCode4");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator1", "Type2");
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator1", "Type1");
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals2() {
        System.out.println("equals2");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator2", "Type1");
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals3() {
        System.out.println("equals3");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = new Operator("Operator1", "Type2");
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals4() {
        System.out.println("equals4");
        Operator instance = new Operator("Operator1", "Type1");
        Operator instance2 = instance;
        assertEquals(instance, instance2);
    }

    @Test
    public void testCloneConstructor() {
        System.out.println("cloneConstructor");
        Operator instance = new Operator("Operator1", "Type1");
        instance.getGeneratedMutants().add(new Mutant("Mutant1", null, new Program("Program1", null)));
        instance.getGeneratedMutants().add(new Mutant("Mutant2", null, new Program("Program1", null)));
        Operator instance2 = new Operator(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getType(), instance2.getType());
        assertArrayEquals(instance.getGeneratedMutants().toArray(), instance2.getGeneratedMutants().toArray());
    }

}
