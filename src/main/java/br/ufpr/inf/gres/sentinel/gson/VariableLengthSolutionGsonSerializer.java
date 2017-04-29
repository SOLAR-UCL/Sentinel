package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.gson.*;
import java.lang.reflect.Type;

public class VariableLengthSolutionGsonSerializer<T extends Number> implements JsonSerializer<VariableLengthSolution<T>> {

    @Override
    public JsonElement serialize(VariableLengthSolution<T> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonSolution = new JsonObject();

        JsonArray jsonObjectives = new JsonArray();
        for (int i = 0; i < src.getNumberOfObjectives(); i++) {
            jsonObjectives.add(src.getObjective(i));
        }

        jsonSolution.add("objectives", jsonObjectives);
        jsonSolution.add("quantity", context.serialize(src.getAttribute("Quantity")));
        jsonSolution.add("evaluation", context.serialize(src.getAttribute("Evaluation")));
        jsonSolution.add("consumedItemsCount", context.serialize(src.getAttribute("Consumed Items Count")));
        jsonSolution.add("variables", context.serialize(src.getVariablesCopy()));
        jsonSolution.add("strategy", context.serialize(src.getAttribute("Strategy"), Strategy.class));
        return jsonSolution;
    }

}
