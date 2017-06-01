package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageCPUTime;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageQuantity;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl.AverageScore;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Giovani Guizzo
 */
public class ObjectiveFunctionFactory {

    /**
     *
     * @return
     */
    public static List<ObjectiveFunction> createAllObjectiveFunctions() {
        return getAllObjectiveFunctions().stream().map(ObjectiveFunctionFactory::createObjectiveFunction).collect(Collectors.toList());
    }

    /**
     *
     * @param name
     * @return
     */
    public static ObjectiveFunction createObjectiveFunction(String name) {
        switch (name.toLowerCase()) {
            case ObjectiveFunction.AVERAGE_CPU_TIME:
                return new AverageCPUTime();
            case ObjectiveFunction.AVERAGE_QUANTITY:
                return new AverageQuantity();
            case ObjectiveFunction.AVERAGE_SCORE:
                return new AverageScore();
            default:
                throw new IllegalArgumentException("I could not find the objective function " + name + "!\n"
                        + "Available options are: " + Joiner.on(", ").join(Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_QUANTITY, ObjectiveFunction.AVERAGE_SCORE)) + ".");
        }
    }

    /**
     *
     * @param names
     * @return
     */
    public static Set<ObjectiveFunction> createObjectiveFunctions(List<String> names) {
        return names.stream().map(ObjectiveFunctionFactory::createObjectiveFunction).collect(Collectors.toSet());
    }

    /**
     *
     * @return
     */
    public static List<String> getAllObjectiveFunctions() {
        List<String> names = new ArrayList<>();
        names.add(ObjectiveFunction.AVERAGE_CPU_TIME);
        names.add(ObjectiveFunction.AVERAGE_QUANTITY);
        names.add(ObjectiveFunction.AVERAGE_SCORE);
        return names;
    }

}
