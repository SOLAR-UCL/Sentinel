package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness;

import java.util.Objects;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class ObjectiveFunction<T> {

    public static final String AVERAGE_QUANTITY = "averagequantity";
    public static final String AVERAGE_SCORE = "averagescore";
    public static final String AVERAGE_CPU_TIME = "averagecputime";

    public abstract String getName();

    public abstract Double computeFitness(Solution<T> solution);

    public Double getWorstValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectiveFunction<?> other = (ObjectiveFunction<?>) obj;
        return Objects.equals(this.getName(), other.getName());
    }

}
