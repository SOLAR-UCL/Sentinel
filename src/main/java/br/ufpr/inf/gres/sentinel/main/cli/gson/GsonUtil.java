package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 *
 * @author Giovani Guizzo
 */
public class GsonUtil {

    private final Gson gson;

    public GsonUtil() throws IOException {
        this(new TrainingArgs());
    }

    public GsonUtil(TrainingArgs trainingArgs) throws IOException {
        gson = new GsonBuilder()
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(DefaultVariableLengthIntegerSolution.class, new VariableLengthSolutionGsonDeserializer(trainingArgs))
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonSerializer())
                .registerTypeAdapter(VariableLengthSolution.class, new VariableLengthSolutionGsonDeserializer(trainingArgs))
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .setPrettyPrinting()
                .create();
    }

    public String toJson(ResultWrapper result) {
        return gson.toJson(result);
    }

    public ResultWrapper fromJson(Path jsonFilePath) throws IOException {
        return this.fromJson(jsonFilePath.toFile());
    }

    public ResultWrapper fromJson(String jsonFilePath) throws IOException {
        return this.fromJson(new File(jsonFilePath));
    }

    public ResultWrapper fromJson(File jsonFile) throws IOException {
        try (BufferedReader reader = Files.newReader(jsonFile, Charset.defaultCharset())) {
            return gson.fromJson(reader, ResultWrapper.class);
        }
    }

}
