package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.main.cli.SentinelTraining;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import com.google.gson.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class VariableLengthSolutionGsonDeserializer implements JsonDeserializer<VariableLengthSolution<Integer>> {

    private final MutationStrategyGenerationProblem problem;

    public VariableLengthSolutionGsonDeserializer() throws IOException {
        TrainingArgs trainingArgs = new TrainingArgs();
        problem = SentinelTraining.buildProblem(SentinelTraining.buildFacade(trainingArgs), trainingArgs);
    }

    public VariableLengthSolutionGsonDeserializer(TrainingArgs trainingArgs) throws IOException {
        problem = SentinelTraining.buildProblem(SentinelTraining.buildFacade(trainingArgs), trainingArgs);
    }

    @Override
    public VariableLengthSolution<Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DefaultVariableLengthIntegerSolution solution = new DefaultVariableLengthIntegerSolution(problem);

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray objectives = jsonObject.get("objectives").getAsJsonArray();
        for (int i = 0; i < objectives.size(); i++) {
            solution.setObjective(i, objectives.get(i).getAsDouble());
        }

        solution.clearVariables();
        JsonElement variables = jsonObject.get("variables");
        if (variables != null) {
            for (JsonElement jsonElement : variables.getAsJsonArray()) {
                solution.addVariable(jsonElement.getAsInt());
            }
        }

        JsonElement quantity = jsonObject.get("quantity");
        if (quantity != null) {
            solution.setAttribute("Quantity", quantity.getAsDouble());
        }

        JsonElement consumedItemsCount = jsonObject.get("consumedItemsCount");
        if (consumedItemsCount != null) {
            solution.setAttribute("Consumed Items Count", consumedItemsCount.getAsInt());
        }
        return solution;
    }

}
