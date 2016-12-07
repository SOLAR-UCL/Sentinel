package br.ufpr.inf.gres.sentinel.base.solution;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import java.io.File;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class SolutionTest {

    public SolutionTest() {
    }

    @Test
    public void testCloneConstructot() {
        System.out.println("cloneConstructor");
        Solution solution = new Solution();

        Mutant mutant = new Mutant("Mutant1", new File("File1"), new Program("Program1", null));
        Mutant mutant2 = new Mutant("Mutant2", new File("File2"), new Program("Program1", null));
        Mutant mutant3 = new Mutant("Mutant3", new File("File2"), new Program("Program1", null));
        Operator operator = new Operator("Operator1", "Type1");
        TestCase testCase = new TestCase("Test1");

        mutant.getKillingTestCases().add(testCase);
        mutant.getConstituentMutants().add(mutant2);
        mutant.getConstituentMutants().add(mutant3);
        mutant.getOperators().add(operator);

        mutant2.getKillingTestCases().add(testCase);
        mutant2.getOperators().add(operator);

        mutant3.getKillingTestCases().add(testCase);
        mutant3.getOperators().add(operator);

        operator.getGeneratedMutants().add(mutant);
        operator.getGeneratedMutants().add(mutant2);
        operator.getGeneratedMutants().add(mutant3);

        solution.getMutants().add(mutant);
        solution.getMutants().add(mutant2);
        solution.getMutants().add(mutant3);
        solution.getOperators().add(operator);

        Solution solution2 = new Solution(solution);

        assertNotSame(solution, solution2);
        assertArrayEquals(solution.getMutants().toArray(), solution2.getMutants().toArray());
        assertArrayEquals(solution.getOperators().toArray(), solution2.getOperators().toArray());

        for (int i = 0; i < solution.getMutants().size(); i++) {
            Mutant temp1 = solution.getMutants().get(i);
            Mutant temp2 = solution2.getMutants().get(i);
            assertNotSame(temp1, temp2);
            assertNotSame(temp1.getOperators().get(0), temp2.getOperators().get(0));
            assertEquals(temp1.isHigherOrder(), temp2.isHigherOrder());
            if (temp1.isHigherOrder()) {
                assertNotSame(temp1.getConstituentMutants().get(0), temp2.getConstituentMutants().get(0));
            }
        }

        for (int i = 0; i < solution.getOperators().size(); i++) {
            Operator temp1 = solution.getOperators().get(i);
            Operator temp2 = solution2.getOperators().get(i);
            assertNotSame(temp1, temp2);
            assertNotSame(temp1.getGeneratedMutants().get(0), temp2.getGeneratedMutants().get(0));
        }
    }

}
