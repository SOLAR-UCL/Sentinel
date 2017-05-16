package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunctionFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.main.cli.SentinelTraining;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import com.google.gson.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class VariableLengthSolutionGsonDeserializer implements JsonDeserializer<VariableLengthSolution<Integer>> {

    private final MutationStrategyGenerationProblem problem;

    /**
     *
     * @throws IOException
     */
    public VariableLengthSolutionGsonDeserializer() throws IOException {
        TrainingArgs trainingArgs = new TrainingArgs();
        this.problem = SentinelTraining.buildProblem(SentinelTraining.buildFacade(trainingArgs), trainingArgs);
    }

    /**
     *
     * @param trainingArgs
     * @throws IOException
     */
    public VariableLengthSolutionGsonDeserializer(TrainingArgs trainingArgs) throws IOException {
        this.problem = SentinelTraining.buildProblem(SentinelTraining.buildFacade(trainingArgs), trainingArgs);
    }

    @Override
    public VariableLengthSolution<Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DefaultVariableLengthIntegerSolution solution = new DefaultVariableLengthIntegerSolution(this.problem);

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

        List<ObjectiveFunction> objectiveFunctions = ObjectiveFunctionFactory.createAllObjectiveFunctions();
        for (ObjectiveFunction objectiveFunction : objectiveFunctions) {
            solution.setAttribute(objectiveFunction.getName(), json);
            JsonElement objective = jsonObject.get(objectiveFunction.getName());
            if (objective != null) {
                solution.setAttribute(objectiveFunction.getName(), objective.getAsDouble());
            }
        }

        JsonElement evaluation = jsonObject.get("evaluation");
        if (evaluation != null) {
            solution.setAttribute("Evaluation Found", evaluation.getAsInt());
        }

        JsonElement consumedItemsCount = jsonObject.get("consumedItemsCount");
        if (consumedItemsCount != null) {
            solution.setAttribute("Consumed Items Count", consumedItemsCount.getAsInt());
        }
        return solution;
    }

}
