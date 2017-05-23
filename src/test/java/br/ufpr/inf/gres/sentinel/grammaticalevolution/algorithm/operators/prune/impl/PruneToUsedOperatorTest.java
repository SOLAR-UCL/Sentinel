package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Lists;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class PruneToUsedOperatorTest {

    private static MutationStrategyGenerationProblem problem;

    @BeforeClass
    public static void setUpClass() throws IOException {
        problem = new MutationStrategyGenerationProblem(GrammarFiles.getDefaultGrammarPath(),
                10,
                15,
                1,
                10,
                2,
                1,
                1,
                null,
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
    }

    @Test
    public void execute() {
        PruneToUsedOperator<Integer> operator = new PruneToUsedOperator<>(1.0);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));
        solution.setAttribute("Consumed Items Count", 7);

        solution = operator.execute(solution);
        assertEquals(7, solution.getNumberOfVariables());
    }

    @Test
    public void execute2() {
        PruneToUsedOperator<Integer> operator = new PruneToUsedOperator<>(1.0);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));
        solution.setAttribute("Consumed Items Count", 0);

        solution = operator.execute(solution);
        assertEquals(17, solution.getNumberOfVariables());
    }

    @Test
    public void execute3() {
        PruneToUsedOperator<Integer> operator = new PruneToUsedOperator<>(1.0);

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.clearVariables();
        solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));

        solution = operator.execute(solution);
        assertEquals(17, solution.getNumberOfVariables());
    }

}
