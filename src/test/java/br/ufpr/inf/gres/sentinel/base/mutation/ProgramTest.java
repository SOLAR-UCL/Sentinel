package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class ProgramTest {

    public ProgramTest() {
    }

    @Test
    public void testHashCode() {
        Program instance = new Program("Program1", null);
        Program instance2 = new Program("Program2", null);
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        Program instance = new Program("Program1", null);
        Program instance2 = new Program("Program1", null);
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        Program instance = new Program("Program1", null);
        int result = instance.hashCode();
        int result2 = instance.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testEquals() {
        Program instance = new Program("Program1", null);
        assertEquals(instance, instance);
    }

    @Test
    public void testEquals2() {
        Program instance = new Program("Program1", null);
        Program instance2 = new Program("Program1", null);
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals3() {
        Program instance = new Program("Program1", null);
        Program instance2 = new Program("Program2", null);
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testCloneConstructor() {
        Program instance = new Program("Program1", new File("Test"));
        Program instance2 = new Program(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getSourceFile(), instance2.getSourceFile());
    }

}
