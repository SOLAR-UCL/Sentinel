package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;
import com.google.common.base.Splitter;
import com.google.gson.*;
import java.lang.reflect.Type;

public class VariableLengthSolutionGsonSerializer<T extends Number> implements JsonSerializer<VariableLengthSolution<T>> {

    @Override
    public JsonElement serialize(VariableLengthSolution<T> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonSolution = new JsonObject();

        JsonArray jsonVariables = new JsonArray();
        for (T variable : src.getVariablesCopy()) {
            jsonVariables.add(variable);
        }

        JsonArray jsonObjectives = new JsonArray();
        for (int i = 0; i < src.getNumberOfObjectives(); i++) {
            jsonObjectives.add(src.getObjective(i));
        }
        Strategy strategy = (Strategy) src.getAttribute("Strategy");

        JsonElement jsonStrategy;
        if (strategy == null) {
            jsonStrategy = new JsonPrimitive("Invalid!");
        } else {
            String toString = strategy.toString().replaceAll("\\t", "");
            Iterable<String> split = Splitter.on("\n").split(toString);
            JsonArray jsonStrategyArray = new JsonArray();
            for (String string : split) {
                jsonStrategyArray.add(string);
            }
            jsonStrategy = jsonStrategyArray;
        }

        jsonSolution.add("variables", jsonVariables);
        jsonSolution.add("objectives", jsonObjectives);
        jsonSolution.add("strategy", jsonStrategy);
        return jsonSolution;
    }

}
