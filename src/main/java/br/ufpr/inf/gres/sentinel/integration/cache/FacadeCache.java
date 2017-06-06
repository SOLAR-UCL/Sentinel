package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class FacadeCache {

    private HashMap<Program, CacheHolder> cache;
    private List<Operator> allOperators;
    private String outputDirectory;
    private String inputDirectory;

    public FacadeCache() {
        cache = new HashMap<>();
    }

    public FacadeCache(String inputDirectory, String outputDirectory) {
        this();
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        readCache();
    }

    public boolean isCached(Program program) {
        return getOrCreateCache(program).isCached;
    }

    public void setCached(Program program) {
        this.cache.get(program).isCached = true;
    }

    public void clearCache(Program program) {
        this.cache.remove(program);
    }

    public void clearCache() {
        this.cache = new HashMap<>();
    }

    public void recordOperatorCPUTime(Program program, Operator operator, long cpuTime) {
        ListMultimap<Operator, Long> times = getOrCreateCache(program).operatorsCPUTime;
        times.put(operator, cpuTime);
    }

    public void recordOperatorExecutionTime(Program program, Operator operator, long executionTime) {
        ListMultimap<Operator, Long> times = getOrCreateCache(program).operatorsExecutionTime;
        times.put(operator, executionTime);
    }

    public void recordMutantCPUTime(Program program, Mutant mutant, long cpuTime) {
        ListMultimap<Mutant, Long> times = getOrCreateCache(program).mutantsCPUTime;
        times.put(mutant, cpuTime);
    }

    public void recordMutantExecutionTime(Program program, Mutant mutant, long executionTime) {
        ListMultimap<Mutant, Long> times = getOrCreateCache(program).mutantsExecutionTime;
        times.put(mutant, executionTime);
    }

    public void setAllOperators(List<Operator> allOperators) {
        this.allOperators = allOperators;
    }

    public List<Operator> getAllOperators() {
        ArrayList<Operator> operators = new ArrayList<>();
        for (Operator operator : this.allOperators) {
            operators.add(new Operator(operator));
        }
        return operators;
    }

    public void recordOperatorGeneratedMutants(Program program, Operator operator, List<Mutant> generatedMutants) {
        HashMap<Operator, List<Mutant>> operatorsList = getOrCreateCache(program).generatedMutants;
        operatorsList.putIfAbsent(operator, generatedMutants);
    }

    public void recordMutantKillingTestCases(Program program, Mutant mutantToExecute, SetUniqueList<TestCase> killingTestCases) {
        HashMap<Mutant, List<TestCase>> killingTestCasesList = getOrCreateCache(program).killingTestCases;
        killingTestCasesList.putIfAbsent(mutantToExecute, killingTestCases);
    }

    public List<Mutant> retrieveOperatorExecutionInformation(Program program, Operator operator) {
        operator.setExecutionTime(getOrCreateCache(program).operatorsExecutionTime
                .get(operator)
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(operator.getExecutionTime()));

        operator.setCpuTime(getOrCreateCache(program).operatorsCPUTime
                .get(operator)
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(operator.getCpuTime()));

        List<Mutant> cachedMutants = getOrCreateCache(program).generatedMutants.get(operator);
        List<Mutant> resultMutants = new ArrayList<>();
        for (Mutant cachedMutant : cachedMutants) {
            Mutant newMutant = new Mutant(cachedMutant);
            newMutant.setOperator(operator);
            resultMutants.add(newMutant);
        }

        operator.setGeneratedMutants(SetUniqueList.setUniqueList(resultMutants));

        return resultMutants;
    }

    public void retrieveMutantExecutionInformation(Program program, Mutant mutantToExecute) {
        mutantToExecute.setExecutionTime(getOrCreateCache(program).mutantsExecutionTime
                .get(mutantToExecute)
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(mutantToExecute.getExecutionTime()));

        mutantToExecute.setCpuTime(getOrCreateCache(program).mutantsCPUTime
                .get(mutantToExecute)
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(mutantToExecute.getCpuTime()));

        List<TestCase> newTestCases = new ArrayList<>();
        List<TestCase> cachedTestCases = getOrCreateCache(program).killingTestCases.get(mutantToExecute);
        for (TestCase cachedTestCase : cachedTestCases) {
            TestCase testCase = new TestCase(cachedTestCase);
            newTestCases.add(testCase);
        }

        mutantToExecute.setKillingTestCases(SetUniqueList.setUniqueList(newTestCases));
    }

    private CacheHolder getOrCreateCache(Program program) {
        return this.cache.computeIfAbsent(program, tempProgram -> new CacheHolder(tempProgram));
    }

    public void writeCache() {
        //TODO implement it
    }

    public void readCache() {
        if (this.inputDirectory != null) {
            try {
                Files.walkFileTree(Paths.get(this.inputDirectory), new CacheFileVisitor(this));
            } catch (IOException ex) {
            }
        }
    }

    private static class CacheHolder {

        private Program program;
        private boolean isCached = false;

        private ListMultimap<Operator, Long> operatorsCPUTime;
        private ListMultimap<Operator, Long> operatorsExecutionTime;
        private ListMultimap<Mutant, Long> mutantsCPUTime;
        private ListMultimap<Mutant, Long> mutantsExecutionTime;
        private HashMap<Operator, List<Mutant>> generatedMutants;
        private HashMap<Mutant, List<TestCase>> killingTestCases;

        public CacheHolder(Program program) {
            this.program = program;
            this.operatorsCPUTime = ArrayListMultimap.create();
            this.operatorsExecutionTime = ArrayListMultimap.create();
            this.mutantsCPUTime = ArrayListMultimap.create();
            this.mutantsExecutionTime = ArrayListMultimap.create();
            this.generatedMutants = new HashMap<>();
            this.killingTestCases = new HashMap<>();
        }

    }

    private static class CacheFileVisitor implements FileVisitor<Path> {

        private final FacadeCache facadeCache;
        private final Gson gson;

        private CacheFileVisitor(FacadeCache facadeCache) {
            this.facadeCache = facadeCache;
            this.gson = new GsonBuilder()
                    .registerTypeAdapter(CacheHolder.class, new CacheHolderDeserializer())
                    .create();
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                CacheHolder cache = gson.fromJson(new JsonReader(new FileReader(file.toString())), CacheHolder.class);
                this.facadeCache.cache.put(cache.program, cache);
                return FileVisitResult.TERMINATE;
            } catch (Exception ex) {
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    private static class CacheHolderDeserializer implements JsonDeserializer<CacheHolder> {

        public CacheHolderDeserializer() {
        }

        @Override
        public CacheHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
        }
    }

}
