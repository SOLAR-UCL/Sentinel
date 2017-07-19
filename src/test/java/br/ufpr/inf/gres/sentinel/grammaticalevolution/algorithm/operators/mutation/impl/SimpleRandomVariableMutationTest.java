package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.mutation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.GrammaticalEvolutionAlgorithmTest;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolutionTest;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.io.File;
import static org.junit.Assert.assertArrayEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Giovani Guizzo
 */
public class SimpleRandomVariableMutationTest {

    private static SimpleRandomVariableMutation operator;

    @BeforeClass
    public static void setUp() throws Exception {
        operator = new SimpleRandomVariableMutation(1.0, 0, 10);
        JMetalRandom.getInstance()
                .setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterators.cycle(3)));
    }

    @Test
    public void execute() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp",
                        new File("src/test"));

        GrammaticalEvolutionAlgorithmTest.ProblemStub problemStub
                = new GrammaticalEvolutionAlgorithmTest.ProblemStub(GrammarFiles.getDefaultGrammarPath(),
                        15,
                        100,
                        0,
                        179,
                        0,
                        1,
                        Lists.newArrayList(programUnderTest));
        DefaultVariableLengthIntegerSolution solution1 = new DefaultVariableLengthIntegerSolution(problemStub);
        solution1.clearVariables();
        solution1.addAllVariables(Lists.newArrayList(0, 0, 0, 0, 0));
        VariableLengthSolution<Integer> offspring = operator.execute(solution1);

        assertArrayEquals(new Object[]{3, 3, 3, 3, 3}, offspring.getVariablesCopy().toArray());
    }

}
