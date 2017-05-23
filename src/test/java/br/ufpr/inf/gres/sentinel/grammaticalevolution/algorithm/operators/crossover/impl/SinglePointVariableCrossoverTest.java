package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.operators.crossover.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.GrammaticalEvolutionAlgorithmTest;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolutionTest;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

/**
 * @author Giovani Guizzo
 */
public class SinglePointVariableCrossoverTest {

    private static SinglePointVariableCrossover<Integer> operator;

    @BeforeClass
    public static void setUp() throws Exception {
        operator = new SinglePointVariableCrossover<>(1.0, 6);
    }

    @AfterClass
    public static void tearDown() {
        JMetalRandom.getInstance().setRandomGenerator(new JavaRandomGenerator());
    }

    @Test
    public void execute() throws Exception {
        JMetalRandom.getInstance().setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterators.cycle(2)));
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp",
                        new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

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
        solution1.addAllVariables(Lists.newArrayList(0, 1, 2, 3, 4, 5));
        DefaultVariableLengthIntegerSolution solution2 = new DefaultVariableLengthIntegerSolution(problemStub);
        solution2.clearVariables();
        solution2.addAllVariables(Lists.newArrayList(6, 7, 8, 9, 10));
        List<VariableLengthSolution<Integer>> offspring = operator.execute(Lists.newArrayList(solution1, solution2));

        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, solution1.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{6, 7, 8, 9, 10}, solution2.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{0, 1, 8, 9, 10}, offspring.get(0).getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{6, 7, 2, 3, 4, 5}, offspring.get(1).getVariablesCopy().toArray());
    }

    @Test
    public void execute2() throws Exception {
        Program programUnderTest
                = new Program("br.ufpr.inf.gres.TriTyp",
                        new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

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
        solution1.addAllVariables(Lists.newArrayList(0, 1, 2, 3, 4, 5));
        DefaultVariableLengthIntegerSolution solution2 = new DefaultVariableLengthIntegerSolution(problemStub);
        solution2.clearVariables();
        solution2.addAllVariables(Lists.newArrayList(6, 7, 8, 9, 10));

        JMetalRandom.getInstance().setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterators.cycle(0, 4)));
        List<VariableLengthSolution<Integer>> offspring = operator.execute(Lists.newArrayList(solution1, solution2));

        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, solution1.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{6, 7, 8, 9, 10}, solution2.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{10}, offspring.get(0).getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{6, 7, 8, 9, 0, 1}, offspring.get(1).getVariablesCopy().toArray());

        JMetalRandom.getInstance().setRandomGenerator(new VariableLengthSolutionTest.PseudoRandomGeneratorStub(Iterators.cycle(5, 0)));
        offspring = operator.execute(Lists.newArrayList(solution1, solution2));

        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, solution1.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{6, 7, 8, 9, 10}, solution2.getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 6}, offspring.get(0).getVariablesCopy().toArray());
        assertArrayEquals(new Object[]{5}, offspring.get(1).getVariablesCopy().toArray());
    }

    @Test
    public void getNumberOfParents() throws Exception {
        assertEquals(2, operator.getNumberOfParents());
    }

}
