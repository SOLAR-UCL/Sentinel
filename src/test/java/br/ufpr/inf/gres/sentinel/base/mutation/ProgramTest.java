package br.ufpr.inf.gres.sentinel.base.mutation;

import java.io.File;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class ProgramTest {

    public ProgramTest() {
    }

    @Test
    public void testCloneConstructor() {
        Program instance = new Program("Program1", new File("Test"));
        Program instance2 = new Program(instance);
        assertEquals(instance, instance2);
        assertEquals(instance.getSourceFile(), instance2.getSourceFile());
    }

    @Test
    public void testEquals() {
        Program instance = new Program("Program1", "");
        assertEquals(instance, instance);
    }

    @Test
    public void testEquals2() {
        Program instance = new Program("Program1", "");
        Program instance2 = new Program("Program1", "");
        assertEquals(instance, instance2);
    }

    @Test
    public void testEquals3() {
        Program instance = new Program("Program1", "");
        Program instance2 = new Program("Program2", "");
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals4() {
        Program instance = new Program("Program1", "");
        Program instance2 = null;
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testEquals5() {
        Program instance = new Program("Program1", "");
        Object instance2 = new Object();
        assertNotEquals(instance, instance2);
    }

    @Test
    public void testGetAndSetFile() {
        Program instance = new Program("Program1", new File("Test"));
        instance.setSourceFile(new File("SourceFile"));
        File result = instance.getSourceFile();
        assertEquals(new File("SourceFile"), result);
    }

    @Test
    public void testGetAndSetNameFullName() {
        Program instance = new Program("Program1", new File("Test"));
        instance.setName("ProgramTest");
        String result = instance.getName();
        assertEquals("ProgramTest", result);
    }

    @Test
    public void testHashCode() {
        Program instance = new Program("Program1", "");
        Program instance2 = new Program("Program2", "");
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertNotEquals(result, result2);
    }

    @Test
    public void testHashCode2() {
        Program instance = new Program("Program1", "");
        Program instance2 = new Program("Program1", "");
        int result = instance.hashCode();
        int result2 = instance2.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testHashCode3() {
        Program instance = new Program("Program1", "");
        int result = instance.hashCode();
        int result2 = instance.hashCode();
        assertEquals(result, result2);
    }

    @Test
    public void testToString() {
        Program instance = new Program("Program1", new File("Test"));
        assertEquals("Program1", instance.toString());
    }

    @Test
    public void testAttributes() {
        Program instance = new Program("Program1", new File("Test"));
        instance.putAttribute("TestAttribute", "TestValue");
        assertEquals("TestValue", instance.getAttribute("TestAttribute"));
        assertEquals("TestValue", instance.getAttributes().get("TestAttribute"));
        instance.removeAttribute("TestAttribute");
        assertTrue(instance.getAttributes().isEmpty());
        HashMap<String, Object> map = new HashMap<>();
        instance.setAttributes(map);
        assertEquals(map, instance.getAttributes());
    }

}
