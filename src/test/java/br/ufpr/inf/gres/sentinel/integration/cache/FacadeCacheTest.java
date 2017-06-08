package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Giovani Guizzo
 */
public class FacadeCacheTest {

    public FacadeCacheTest() {
    }

    @Test
    public void testConstructor() {
        FacadeCache cache = new FacadeCache();
        assertTrue(cache.getCachedPrograms().isEmpty());
    }

    @Test
    public void testConstructor2() {
        FacadeCache cache = new FacadeCache("Unknown", "src" + File.separator + "test" + File.separator + "resources");
        assertTrue(cache.getCachedPrograms().isEmpty());
    }

    @Test
    public void testWriteAndRead() throws IOException {
        Program program = new Program("TestWrite", "./Test");
        program.putAttribute("TestKey", "TestValue");

        Mutant mutant1 = new Mutant("Mutant1", new File("./Mutant1"), program);
        mutant1.getKillingTestCases().add(new TestCase("TestCase1"));
        Mutant mutant2 = new Mutant("Mutant2", new File("./Mutant2"), program);
        Operator operator1 = new Operator("Operator1", "OperatorType");

        mutant1.setOperator(operator1);
        mutant2.setOperator(operator1);
        operator1.getGeneratedMutants().add(mutant1);
        operator1.getGeneratedMutants().add(mutant2);

        FacadeCache cache = new FacadeCache(null, "src"
                + File.separator
                + "test"
                + File.separator
                + "resources");

        cache.retrieveOperatorExecutionInformation(program, operator1);
        assertEquals(0.0, operator1.getCpuTime(), 0.0001);
        assertEquals(0.0, operator1.getExecutionTime(), 0.0001);

        cache.recordOperatorGeneratedMutants(program, operator1, operator1.getGeneratedMutants());

        cache.recordOperatorCPUTime(program, operator1, 1000L);

        cache.recordMutantKillingTestCases(program, mutant1, mutant1.getKillingTestCases());
        cache.recordMutantKillingTestCases(program, mutant2, mutant2.getKillingTestCases());

        cache.recordMutantExecutionTime(program, mutant1, 100L);
        cache.recordMutantExecutionTime(program, mutant2, 200L);
        cache.recordMutantCPUTime(program, mutant2, 400L);
        cache.recordMutantCPUTime(program, mutant2, 900L);
        cache.recordMutantCPUTime(program, mutant2, 10000L);

        cache.retrieveOperatorExecutionInformation(program, operator1);
        cache.retrieveMutantExecutionInformation(program, mutant1);
        cache.retrieveMutantExecutionInformation(program, mutant2);

        Path expectedOutputFile1 = Paths.get("src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite.json");
        Files.deleteIfExists(expectedOutputFile1);

        Path expectedOutputFile2 = Paths.get("src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + ".cache"
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite.json");
        Files.deleteIfExists(expectedOutputFile2);

        cache.writeCache();
        assertFalse(Files.exists(expectedOutputFile1));

        cache.setCached(program);
        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile1));

        FacadeCache cache2 = new FacadeCache("src"
                + File.separator
                + "test"
                + File.separator
                + "resources",
                "src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + ".cache");
        assertEquals(cache.isCached(program), cache2.isCached(program));

        Mutant mutant3 = new Mutant("Mutant1", new File("./Mutant1"), program);
        mutant1.getKillingTestCases().add(new TestCase("TestCase1"));
        Mutant mutant4 = new Mutant("Mutant2", new File("./Mutant2"), program);
        Operator operator2 = new Operator("Operator1", "OperatorType");

        cache2.retrieveOperatorExecutionInformation(program, operator2);
        cache2.retrieveMutantExecutionInformation(program, mutant3);
        cache2.retrieveMutantExecutionInformation(program, mutant4);

        assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
        assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
        assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());

        assertEquals(mutant1.getCpuTime(), mutant3.getCpuTime(), 0.0001);
        assertEquals(mutant1.getExecutionTime(), mutant3.getExecutionTime(), 0.0001);
        assertEquals(mutant2.getCpuTime(), mutant4.getCpuTime(), 0.0001);
        assertEquals(mutant2.getExecutionTime(), mutant4.getExecutionTime(), 0.0001);

        assertEquals(mutant1.getOperator(), mutant3.getOperator());
        assertEquals(mutant2.getOperator(), mutant4.getOperator());

        assertArrayEquals(mutant1.getKillingTestCases().toArray(), mutant3.getKillingTestCases().toArray());
        assertArrayEquals(mutant2.getKillingTestCases().toArray(), mutant4.getKillingTestCases().toArray());

        cache2.writeCache();
        assertTrue(Files.exists(expectedOutputFile2));

        assertTrue(FileUtils.contentEquals(expectedOutputFile1.toFile(), expectedOutputFile2.toFile()));

        FileUtils.deleteDirectory(Paths.get("src"
                + File.separator
                + "test"
                + File.separator
                + "resources"
                + File.separator
                + ".cache").toFile());
    }

    @Test
    public void testCached() {
        FacadeCache cache = new FacadeCache();
        Program program = new Program("TestWrite", "./Test");

        assertEquals(0, cache.getCachedPrograms().size());
        assertFalse(cache.isCached(program));
        assertEquals(0, cache.getCachedPrograms().size());
        cache.setCached(program);
        assertEquals(1, cache.getCachedPrograms().size());
        assertTrue(cache.isCached(program));
        assertEquals(1, cache.getCachedPrograms().size());
        cache.setCached(program, false);
        assertEquals(0, cache.getCachedPrograms().size());
        assertFalse(cache.isCached(program));

        cache.setCached(program);
        cache.clearCache(program);
        assertEquals(0, cache.getCachedPrograms().size());
        assertFalse(cache.isCached(program));

        cache.setCached(program);
        cache.clearCache();
        assertEquals(0, cache.getCachedPrograms().size());
        assertFalse(cache.isCached(program));
    }

}
