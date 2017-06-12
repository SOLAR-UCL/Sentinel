package br.ufpr.inf.gres.sentinel.base.mutation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Giovani Guizzo
 */
public class TestCaseTest {

    public TestCaseTest() {
    }

    @Test
    public void testCloneConstructor() {
        TestCase instance = new TestCase("Test1");
        TestCase instance2 = new TestCase(instance);
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test1");
        assertEquals(testCase, testCase2);
    }

    @Test
    public void testEquals2() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = testCase;
        assertEquals(testCase, testCase2);
    }

    @Test
    public void testEquals3() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test2");
        assertNotEquals(testCase, testCase2);
    }

    @Test
    public void testEquals4() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = null;
        assertNotEquals(testCase, testCase2);
    }

    @Test
    public void testEquals5() {
        TestCase testCase = new TestCase("Test1");
        Object testCase2 = new Object();
        assertNotEquals(testCase, testCase2);
    }

    @Test
    public void testGetAndSetName() {
        TestCase testCase = new TestCase("Test1");
        testCase.setName("TestTest");
        String result = testCase.getName();
        assertEquals("TestTest", result);
    }

    @Test
    public void testHashCode() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test1");
        assertEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testHashCode2() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = testCase;
        assertEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testHashCode3() {
        TestCase testCase = new TestCase("Test1");
        TestCase testCase2 = new TestCase("Test2");
        assertNotEquals(testCase.hashCode(), testCase2.hashCode());
    }

    @Test
    public void testToString() {
        TestCase testCase = new TestCase("Test1");
        assertEquals("Test1", testCase.toString());
    }

}
