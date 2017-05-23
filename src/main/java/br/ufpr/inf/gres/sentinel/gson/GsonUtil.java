package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

/**
 *
 * @author Giovani Guizzo
 */
public class GsonUtil {

    private final Gson gson;

    /**
     *
     * @throws IOException
     */
    public GsonUtil() throws IOException {
        this(new TrainingArgs());
    }

    /**
     *
     * @param trainingArgs
     * @throws IOException
     */
    public GsonUtil(TrainingArgs trainingArgs) throws IOException {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonDeserializer(trainingArgs))
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonDeserializer(trainingArgs))
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .setPrettyPrinting()
                .create();
    }

    /**
     *
     * @param jsonFilePath
     * @return
     * @throws IOException
     */
    public ResultWrapper fromJson(Path jsonFilePath) throws IOException {
        return this.fromJson(jsonFilePath.toFile());
    }

    /**
     *
     * @param jsonFilePath
     * @return
     * @throws IOException
     */
    public ResultWrapper fromJson(String jsonFilePath) throws IOException {
        return this.fromJson(new File(jsonFilePath));
    }

    /**
     *
     * @param jsonFile
     * @return
     * @throws IOException
     */
    public ResultWrapper fromJson(File jsonFile) throws IOException {
        try (BufferedReader reader = Files.newReader(jsonFile, Charset.defaultCharset())) {
            return this.gson.fromJson(reader, ResultWrapper.class);
        }
    }

    /**
     *
     * @param result
     * @return
     */
    public String toJson(ResultWrapper result) {
        return this.gson.toJson(result);
    }

    /**
     *
     * @param inputDirectoryPath
     * @param inputFilesGlob
     * @return @throws IOException
     */
    public ListMultimap<String, ResultWrapper> getResultsFromJsonFiles(String inputDirectoryPath, String inputFilesGlob) throws IOException {
        File inputDirectory = new File(inputDirectoryPath);
        ListMultimap<String, ResultWrapper> results = ArrayListMultimap.create();

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + inputFilesGlob);
        try (Stream<Path> jsonFiles = java.nio.file.Files.walk(inputDirectory.toPath()).filter(matcher::matches)) {
            jsonFiles.forEach((jsonFile) -> {
                try {
                    ResultWrapper result = fromJson(jsonFile);
                    results.put(result.getSession().isEmpty() ? "EMPTY_SESSION" : result.getSession(), result);
                } catch (Exception ex) {
                    // Not a result file.
                }
            });
        }
        return results;
    }

}
