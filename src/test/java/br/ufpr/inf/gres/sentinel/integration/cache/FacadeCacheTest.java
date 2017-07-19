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
import static org.junit.Assert.*;
import org.junit.Test;

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
    public void testConstructor2() throws IOException {
        Path outputDirectory = Files.createTempDirectory("Sentinel");
        FacadeCache cache = new FacadeCache("Unknown", outputDirectory.toString());
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

        String tempOutput = Files.createTempDirectory("Sentinel").toString();

        FacadeCache cache = new FacadeCache(null, tempOutput);

        cache.retrieveOperatorExecutionInformation(program, operator1);
        assertEquals(0.0, operator1.getCpuTime(), 0.0001);
        assertEquals(0.0, operator1.getExecutionTime(), 0.0001);

        cache.recordOperatorGeneratedMutants(program, operator1, operator1.getGeneratedMutants());

        cache.recordOperatorCPUTime(program, operator1, 1000L);

        cache.recordMutantKillingTestCases(program, mutant1, mutant1.getKillingTestCases());
        mutant1.getKillingTestCases().add(new TestCase("TestCase2"));
        cache.recordMutantKillingTestCases(program, mutant1, mutant1.getKillingTestCases());

        cache.recordMutantKillingTestCases(program, mutant2, mutant2.getKillingTestCases());

        cache.recordMutantExecutionTime(program, mutant1, 100L);
        cache.recordMutantExecutionTime(program, mutant2, 200L);
        cache.recordMutantCPUTime(program, mutant2, 400L);
        cache.recordMutantCPUTime(program, mutant2, 900L);
        cache.recordMutantCPUTime(program, mutant2, 10000L);

        Path expectedOutputFile1 = Paths.get(tempOutput
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite.json");
        Files.deleteIfExists(expectedOutputFile1);

        Path expectedOutputFile2 = Paths.get(tempOutput
                + File.separator
                + ".cache"
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite.json");
        Files.deleteIfExists(expectedOutputFile2);

        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile1));

        cache.setCached(program);
        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile1));

        cache.retrieveOperatorExecutionInformation(program, operator1);
        cache.retrieveMutantExecutionInformation(program, mutant1);
        cache.retrieveMutantExecutionInformation(program, mutant2);

        FacadeCache cache2 = new FacadeCache(tempOutput,
                tempOutput
                + File.separator
                + ".cache");
        assertEquals(cache.isCached(program), cache2.isCached(program));

        Mutant mutant3 = new Mutant("Mutant1", new File("./Mutant1"), program);
        Mutant mutant4 = new Mutant("Mutant2", new File("./Mutant2"), program);
        Operator operator2 = new Operator("Operator1", "OperatorType");

        mutant3.setOperator(operator1);
        mutant4.setOperator(operator1);

        cache2.retrieveOperatorExecutionInformation(program, operator2);
        cache2.retrieveMutantExecutionInformation(program, mutant3);
        cache2.retrieveMutantExecutionInformation(program, mutant4);

        assertEquals(operator1.getCpuTime(), operator2.getCpuTime(), 0.0001);
        assertEquals(operator1.getExecutionTime(), operator2.getExecutionTime(), 0.0001);
        assertEquals(2, operator2.getGeneratedMutants().size());
        assertArrayEquals(operator1.getGeneratedMutants().toArray(), operator2.getGeneratedMutants().toArray());

        assertEquals(mutant1.getCpuTime(), mutant3.getCpuTime(), 0.0001);
        assertEquals(mutant1.getExecutionTime(), mutant3.getExecutionTime(), 0.0001);
        assertEquals(mutant2.getCpuTime(), mutant4.getCpuTime(), 0.0001);
        assertEquals(mutant2.getExecutionTime(), mutant4.getExecutionTime(), 0.0001);

        assertEquals(operator2, mutant3.getOperator());
        assertEquals(mutant1.getOperator(), mutant3.getOperator());
        assertEquals(operator2, mutant4.getOperator());
        assertEquals(mutant2.getOperator(), mutant4.getOperator());

        assertEquals(2, mutant3.getKillingTestCases().size());
        assertArrayEquals(mutant1.getKillingTestCases().toArray(), mutant3.getKillingTestCases().toArray());
        assertEquals(0, mutant4.getKillingTestCases().size());
        assertArrayEquals(mutant2.getKillingTestCases().toArray(), mutant4.getKillingTestCases().toArray());

        cache2.writeCache();
        assertTrue(Files.exists(expectedOutputFile2));

        assertTrue(FileUtils.contentEquals(expectedOutputFile1.toFile(), expectedOutputFile2.toFile()));

        FileUtils.deleteDirectory(Paths.get(tempOutput
                + File.separator
                + ".cache").toFile());
    }

    @Test
    public void testWriteAndRead2() throws IOException {
        String tempOutput = Files.createTempDirectory("Sentinel").toString();

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

        FacadeCache cache = new FacadeCache(null, tempOutput);

        cache.recordOperatorGeneratedMutants(program, operator1, operator1.getGeneratedMutants());
        cache.recordOperatorCPUTime(program, operator1, 1000L);
        cache.recordOperatorExecutionTime(program, operator1, 1000L);

        cache.recordMutantKillingTestCases(program, mutant1, mutant1.getKillingTestCases());
        cache.recordMutantKillingTestCases(program, mutant2, mutant2.getKillingTestCases());

        cache.recordMutantExecutionTime(program, mutant1, 100L);
        cache.recordMutantExecutionTime(program, mutant2, 200L);
        cache.recordMutantCPUTime(program, mutant1, 100L);
        cache.recordMutantCPUTime(program, mutant2, 200L);

        Path expectedOutputFile = Paths.get(tempOutput
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite.json");
        Files.deleteIfExists(expectedOutputFile);

        Path expectedTempOutputFile = Paths.get(tempOutput
                + File.separator
                + ".cache"
                + File.separator
                + "TestWrite_temp.json");

        cache.notifyRunEnded(program);
        cache.notifyRunEnded(program);
        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile));
        assertFalse(Files.exists(expectedTempOutputFile));
        assertFalse(cache.isCached(program));

        cache = new FacadeCache(tempOutput, tempOutput);

        Mutant mutant3 = new Mutant("Mutant1", new File("./Mutant1"), program);
        Mutant mutant4 = new Mutant("Mutant2", new File("./Mutant2"), program);
        Operator operator2 = new Operator("Operator1", "OperatorType");

        mutant3.setOperator(operator2);
        mutant4.setOperator(operator2);

        cache.retrieveOperatorExecutionInformation(program, operator2);
        cache.retrieveMutantExecutionInformation(program, mutant3);
        cache.retrieveMutantExecutionInformation(program, mutant4);

        mutant3.getKillingTestCases().add(new TestCase("TestCase2"));

        assertEquals(1000D, operator2.getCpuTime(), 0.0001);
        assertEquals(1000D, operator2.getExecutionTime(), 0.0001);

        assertEquals(100D, mutant3.getCpuTime(), 0.0001);
        assertEquals(100D, mutant3.getExecutionTime(), 0.0001);
        assertEquals(200D, mutant4.getCpuTime(), 0.0001);
        assertEquals(200D, mutant4.getExecutionTime(), 0.0001);

        cache.recordOperatorGeneratedMutants(program, operator2, operator2.getGeneratedMutants());
        cache.recordOperatorCPUTime(program, operator2, 4000L);
        cache.recordOperatorExecutionTime(program, operator2, 4000L);

        cache.recordMutantKillingTestCases(program, mutant3, mutant3.getKillingTestCases());
        cache.recordMutantKillingTestCases(program, mutant4, mutant4.getKillingTestCases());

        cache.recordMutantExecutionTime(program, mutant3, 400L);
        cache.recordMutantExecutionTime(program, mutant4, 800L);
        cache.recordMutantCPUTime(program, mutant3, 400L);
        cache.recordMutantCPUTime(program, mutant4, 800L);

        cache.notifyRunEnded(program);
        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile));
        assertFalse(Files.exists(expectedTempOutputFile));
        assertFalse(cache.isCached(program));

        cache = new FacadeCache(tempOutput, tempOutput);

        Mutant mutant5 = new Mutant("Mutant1", new File("./Mutant1"), program);
        Mutant mutant6 = new Mutant("Mutant2", new File("./Mutant2"), program);
        Operator operator3 = new Operator("Operator1", "OperatorType");

        mutant5.setOperator(operator3);
        mutant6.setOperator(operator3);

        cache.retrieveOperatorExecutionInformation(program, operator3);
        cache.retrieveMutantExecutionInformation(program, mutant5);
        cache.retrieveMutantExecutionInformation(program, mutant6);

        assertEquals(2000D, operator3.getCpuTime(), 0.0001);
        assertEquals(2000D, operator3.getExecutionTime(), 0.0001);

        assertEquals(200D, mutant5.getCpuTime(), 0.0001);
        assertEquals(200D, mutant5.getExecutionTime(), 0.0001);
        assertEquals(400D, mutant6.getCpuTime(), 0.0001);
        assertEquals(400D, mutant6.getExecutionTime(), 0.0001);

        assertEquals(2, mutant5.getKillingTestCases().size());
        assertEquals(0, mutant6.getKillingTestCases().size());

        cache.setCached(program);
        cache.writeCache();
        assertTrue(Files.exists(expectedOutputFile));
        assertFalse(Files.exists(expectedTempOutputFile));

        cache = new FacadeCache(tempOutput, null);
        assertTrue(cache.isCached(program));

        FileUtils.deleteDirectory(Paths.get(tempOutput
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
