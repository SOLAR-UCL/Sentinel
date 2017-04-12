package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Giovani Guizzo
 */
public class OperationSerializer implements JsonSerializer<Operation> {

    @Override
    public JsonElement serialize(Operation src, Type typeOfSrc, JsonSerializationContext context) {
        if (src instanceof NewBranchOperation) {
            JsonObject element = context.serialize(src, NewBranchOperation.class).getAsJsonObject();

            JsonElement successor = element.get("successor");
            element.remove("successor");
            element.add("successor", successor);

            successor = element.get("secondSuccessor");
            element.remove("secondSuccessor");
            element.add("secondSuccessor", successor);

            return element;
        } else {
            JsonObject element = context.serialize(src, src.getClass()).getAsJsonObject();
            JsonElement successor = element.get("successor");
            element.remove("successor");
            element.add("successor", successor);
            return element;
        }
    }

}
