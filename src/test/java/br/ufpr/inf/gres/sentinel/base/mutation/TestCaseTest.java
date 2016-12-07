package br.ufpr.inf.gres.sentinel.base.mutation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class TestCaseTest {

    public TestCaseTest() {
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test1");
        assertEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testHashCode2() {
        System.out.println("hashCode2");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = testCase;
        assertEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testHashCode3() {
        System.out.println("hashCode3");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test2");
        assertNotEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test1");
        assertEquals(testCase, testCase2);
    }

    @Test
    public void testEquals2() {
        System.out.println("equals2");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = testCase;
        assertEquals(testCase, testCase2);
    }

    @Test
    public void testEquals3() {
        System.out.println("equals3");
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test2");
        assertNotEquals(testCase, testCase2);
    }

    @Test
    public void testCloneConstructor() {
        System.out.println("cloneConstructor");
        TestCase instance = new TestCase("Test1");
        instance.getKillingMutants().add(new Mutant("Mutant1", null, new Program("Program1", null)));
        instance.getKillingMutants().add(new Mutant("Mutant2", null, new Program("Program1", null)));
        TestCase instance2 = new TestCase(instance);
        assertEquals(instance, instance2);
        assertArrayEquals(instance.getKillingMutants().toArray(), instance2.getKillingMutants().toArray());
    }

}
