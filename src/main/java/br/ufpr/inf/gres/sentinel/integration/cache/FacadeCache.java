package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Giovani Guizzo
 */
public class FacadeCache {

    private static final Logger LOGGER = LogManager.getLogger(FacadeCache.class);

    private HashMap<Program, CacheHolder> caches;
    private String outputDirectory;
    private String inputDirectory;

    public FacadeCache() {
        this.caches = new HashMap<>();
    }

    public FacadeCache(String inputDirectory, String outputDirectory) {
        this();
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        if (inputDirectory != null) {
            LOGGER.debug("Trying to load cache from " + inputDirectory);
            this.loadCache();
        }
    }

    public Set<Program> getCachedPrograms() {
        return caches
                .values()
                .stream()
                .filter(cache -> cache.isCached)
                .map(cache -> cache.program)
                .collect(Collectors.toSet());
    }

    public boolean isCached(Program program) {
        return getOrCreateCache(program).isCached;
    }

    public void setCached(Program program) {
        this.setCached(program, true);
    }

    public void setCached(Program program, boolean isCached) {
        this.caches.get(program).isCached = isCached;
    }

    public void clearCache(Program program) {
        this.caches.remove(program);
    }

    public void clearCache() {
        this.caches = new HashMap<>();
    }

    public void recordOperatorCPUTime(Program program, Operator operator, long cpuTime) {
        HashMap<String, List<Long>> times = getOrCreateCache(program).operatorsCPUTime;
        List<Long> longValues = times.computeIfAbsent(operator.toString(), temp -> new ArrayList<>());
        longValues.add(cpuTime);
    }

    public void recordOperatorExecutionTime(Program program, Operator operator, long executionTime) {
        HashMap<String, List<Long>> times = getOrCreateCache(program).operatorsExecutionTime;
        List<Long> longValues = times.computeIfAbsent(operator.toString(), temp -> new ArrayList<>());
        longValues.add(executionTime);
    }

    public void recordMutantCPUTime(Program program, Mutant mutant, long cpuTime) {
        HashMap<String, List<Long>> times = getOrCreateCache(program).mutantsCPUTime;
        List<Long> longValues = times.computeIfAbsent(mutant.toString(), temp -> new ArrayList<>());
        longValues.add(cpuTime);
    }

    public void recordMutantExecutionTime(Program program, Mutant mutant, long executionTime) {
        HashMap<String, List<Long>> times = getOrCreateCache(program).mutantsExecutionTime;
        List<Long> longValues = times.computeIfAbsent(mutant.toString(), temp -> new ArrayList<>());
        longValues.add(executionTime);
    }

    public void recordOperatorGeneratedMutants(Program program, Operator operator, List<Mutant> generatedMutants) {
        CacheHolder cache = getOrCreateCache(program);

        HashMap<String, Operator> operators = cache.operators;
        operators.putIfAbsent(operator.toString(), operator);

        HashMap<String, Mutant> mutants = cache.mutants;
        for (Mutant generatedMutant : generatedMutants) {
            mutants.putIfAbsent(generatedMutant.toString(), generatedMutant);
        }
    }

    public void recordMutantKillingTestCases(Program program, Mutant mutantToExecute, SetUniqueList<TestCase> killingTestCases) {
        Mutant mutant = getOrCreateCache(program).mutants.get(mutantToExecute.toString());
        mutant.setKillingTestCases(killingTestCases);
    }

    public List<Mutant> retrieveOperatorExecutionInformation(Program program, Operator operator) {
        CacheHolder cache = getOrCreateCache(program);

        operator.setExecutionTime(cache.operatorsExecutionTime
                .getOrDefault(operator.toString(), new ArrayList<>())
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(operator.getExecutionTime()));

        operator.setCpuTime(cache.operatorsCPUTime
                .getOrDefault(operator.toString(), new ArrayList<>())
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(operator.getCpuTime()));

        List<Mutant> resultMutants = new ArrayList<>();

        Operator cachedOperator = cache.operators.get(operator.toString());
        if (cachedOperator != null) {
            List<Mutant> cachedMutants = cachedOperator.getGeneratedMutants();
            for (Mutant cachedMutant : cachedMutants) {
                Mutant newMutant = new Mutant(cachedMutant.getName(), cachedMutant.getSourceFile(), cachedMutant.getOriginalProgram());
                newMutant.setOperator(operator);
                resultMutants.add(newMutant);
            }

            operator.setGeneratedMutants(SetUniqueList.setUniqueList(resultMutants));
        }
        return resultMutants;
    }

    public void retrieveMutantExecutionInformation(Program program, Mutant mutantToExecute) {
        CacheHolder cache = getOrCreateCache(program);

        mutantToExecute.setExecutionTime(cache.mutantsExecutionTime
                .getOrDefault(mutantToExecute.toString(), new ArrayList<>())
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(mutantToExecute.getExecutionTime()));

        mutantToExecute.setCpuTime(cache.mutantsCPUTime
                .getOrDefault(mutantToExecute.toString(), new ArrayList<>())
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(mutantToExecute.getCpuTime()));

        List<TestCase> newTestCases = new ArrayList<>();

        Mutant cachedMutant = cache.mutants.get(mutantToExecute.toString());
        if (cachedMutant != null) {
            mutantToExecute.setOperator(new Operator(cachedMutant.getOperator()));
            List<TestCase> cachedTestCases = cachedMutant.getKillingTestCases();
            for (TestCase cachedTestCase : cachedTestCases) {
                TestCase testCase = new TestCase(cachedTestCase);
                newTestCases.add(testCase);
            }
        }

        mutantToExecute.setKillingTestCases(SetUniqueList.setUniqueList(newTestCases));
    }

    private CacheHolder getOrCreateCache(Program program) {
        return this.caches.computeIfAbsent(program, tempProgram -> new CacheHolder(tempProgram));
    }

    public void writeCache() throws IOException {
        if (this.outputDirectory != null) {
            this.writeCache(this.outputDirectory + File.separator + ".cache");
        }
    }

    public void writeCache(String outputFolderPath) throws IOException {
        File outputFolder = new File(outputFolderPath);
        Gson gson = new GsonBuilder()
                //                .setPrettyPrinting()
                .create();
        for (CacheHolder cache : this.caches.values()) {
            if (cache.isCached) {
                File outputFile = new File(outputFolder.getAbsolutePath() + File.separator + cache.program.getName() + ".json");
                com.google.common.io.Files.createParentDirs(outputFile);
                com.google.common.io.Files.write(gson.toJson(cache), outputFile, Charset.defaultCharset());
                LOGGER.debug("Cache file for program " + cache.program.getName() + " was created seccessfully.");
                LOGGER.trace("Output file: " + outputFile.getAbsolutePath());
            }
        }
    }

    public void loadCache() {
        this.loadCache(this.inputDirectory);
    }

    public void loadCache(String inputDirectory) {
        if (inputDirectory != null) {
            try {
                Files.walkFileTree(Paths.get(inputDirectory), new CacheFileVisitor(this));
            } catch (IOException ex) {
            }
        }
    }

    private static class CacheHolder {

        private Program program;
        private boolean isCached = false;

        private HashMap<String, Operator> operators;
        private HashMap<String, Mutant> mutants;

        private HashMap<String, List<Long>> operatorsCPUTime;
        private HashMap<String, List<Long>> operatorsExecutionTime;
        private HashMap<String, List<Long>> mutantsCPUTime;
        private HashMap<String, List<Long>> mutantsExecutionTime;

        public CacheHolder(Program program) {
            this.program = program;
            this.operatorsCPUTime = new HashMap<>();
            this.operatorsExecutionTime = new HashMap<>();
            this.mutantsCPUTime = new HashMap<>();
            this.mutantsExecutionTime = new HashMap<>();
            this.operators = new HashMap<>();
            this.mutants = new HashMap<>();
        }

    }

    private static class CacheFileVisitor implements FileVisitor<Path> {

        private final FacadeCache facadeCache;
        private final Gson gson;

        private CacheFileVisitor(FacadeCache facadeCache) {
            this.facadeCache = facadeCache;
            this.gson = new GsonBuilder()
                    .registerTypeAdapter(CacheHolder.class, new CacheHolderDeserializer())
                    .registerTypeAdapter(SetUniqueList.class, (InstanceCreator<SetUniqueList>) (Type type) -> SetUniqueList.setUniqueList(new ArrayList<>()))
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
                String fileExtension = com.google.common.io.Files.getFileExtension(file.toString());
                if (fileExtension.equals("json")) {
                    LOGGER.trace("Found a json file: " + file.toAbsolutePath().toString());
                    try (JsonReader jsonReader = new JsonReader(new FileReader(file.toFile()))) {
                        CacheHolder cache = gson.fromJson(jsonReader, CacheHolder.class);
                        this.facadeCache.caches.put(cache.program, cache);
                        LOGGER.debug("File is a valid cache for " + cache.program.getName() + ". It was successfully loaded.");
                    }
                }
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
            JsonObject jsonObject = json.getAsJsonObject();
            Program program = context.deserialize(jsonObject.get("program"), Program.class);
            CacheHolder cache = new CacheHolder(program);

            cache.isCached = jsonObject.get("isCached").getAsBoolean();

            cache.operators = context.deserialize(jsonObject.get("operators"), new TypeToken<HashMap<String, Operator>>() {
            }.getType());
            cache.mutants = context.deserialize(jsonObject.get("mutants"), new TypeToken<HashMap<String, Mutant>>() {
            }.getType());

            for (Mutant mutant : cache.mutants.values()) {
                mutant.setOriginalProgram(program);
            }

            for (Operator operator : cache.operators.values()) {
                for (Mutant mutant : operator.getGeneratedMutants()) {
                    mutant.setOperator(operator);
                    mutant.setOriginalProgram(program);
                    cache.mutants.get(mutant.toString()).setOperator(operator);
                }
            }

            Type timesType = new TypeToken<HashMap<String, List<Long>>>() {
            }.getType();

            cache.operatorsCPUTime = context.deserialize(jsonObject.get("operatorsCPUTime"), timesType);
            cache.operatorsExecutionTime = context.deserialize(jsonObject.get("operatorsExecutionTime"), timesType);
            cache.mutantsCPUTime = context.deserialize(jsonObject.get("mutantsCPUTime"), timesType);
            cache.mutantsExecutionTime = context.deserialize(jsonObject.get("mutantsExecutionTime"), timesType);

            return cache;
        }
    }
}
