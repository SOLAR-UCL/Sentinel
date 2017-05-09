package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunctionFactory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.List;

public class VariableLengthSolutionGsonSerializer<T extends Number> implements JsonSerializer<VariableLengthSolution<T>> {

    @Override
    public JsonElement serialize(VariableLengthSolution<T> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonSolution = new JsonObject();

        JsonArray jsonObjectives = new JsonArray();
        for (int i = 0; i < src.getNumberOfObjectives(); i++) {
            jsonObjectives.add(src.getObjective(i));
        }
        jsonSolution.add("objectives", jsonObjectives);

        List<ObjectiveFunction> objectiveFunctions = ObjectiveFunctionFactory.createAllObjectiveFunctions();
        for (ObjectiveFunction objectiveFunction : objectiveFunctions) {
            jsonSolution.add(objectiveFunction.getName(), context.serialize(src.getAttribute(objectiveFunction.getName()), Double.class));
        }

        jsonSolution.add("evaluation", context.serialize(src.getAttribute("Evaluation Found")));
        jsonSolution.add("consumedItemsCount", context.serialize(src.getAttribute("Consumed Items Count")));
        jsonSolution.add("variables", context.serialize(src.getVariablesCopy()));
        jsonSolution.add("strategy", context.serialize(src.getAttribute("Strategy"), Strategy.class));
        return jsonSolution;
    }

}
