/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import br.ufpr.inf.gres.sentinel.integration.pit.PITFacade;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 *
 * @author Giovani
 */
public class RandomEvolutionaryAlgorithmTest {

    private static RandomEvolutionaryAlgorithm<Integer> algorithm;
    private static GrammaticalEvolutionAlgorithmTest.ProblemStub problem;

    @BeforeClass
    public static void setUpClass() throws IOException {
        IntegrationFacade.setIntegrationFacade(new PITFacade(System.getProperty("user.dir")
                + File.separator
                + "src/test/resources/testfiles"));

        Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp",
                new File("src/test/resources"));

        problem = new GrammaticalEvolutionAlgorithmTest.ProblemStub(GrammarFiles.getDefaultGrammarPath(),
                15,
                100,
                0,
                179,
                0,
                1,
                Lists.newArrayList(programUnderTest));

        algorithm = new RandomEvolutionaryAlgorithm<>(problem,
                100,
                50,
                new SequentialSolutionListEvaluator<>());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("Random Search Algorithm with the NSGA-II mechanism of pruning the population", algorithm.getDescription());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Random Search Algorithm", algorithm.getName());
    }

    @Test
    public void run() throws Exception {
        algorithm.run();
        List<VariableLengthSolution<Integer>> result = algorithm.getResult();
        assertEquals(50, result.size());
    }

}
