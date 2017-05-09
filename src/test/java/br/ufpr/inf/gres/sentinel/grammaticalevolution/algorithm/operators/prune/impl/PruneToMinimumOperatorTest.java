package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.prune.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl.MutationStrategyGenerationProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Lists;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class PruneToMinimumOperatorTest {

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
                null,
                Lists.newArrayList(ObjectiveFunction.AVERAGE_CPU_TIME, ObjectiveFunction.AVERAGE_SCORE));
    }

    @Test
    public void execute() throws Exception {
        PruneToMinimumOperator<Integer> operator = new PruneToMinimumOperator<>(1.0, problem.getMinLength());

        VariableLengthSolution<Integer> solution = problem.createSolution();
        solution.addAllVariables(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17));

        solution = operator.execute(solution);
        assertEquals(10, solution.getNumberOfVariables());
    }

}
