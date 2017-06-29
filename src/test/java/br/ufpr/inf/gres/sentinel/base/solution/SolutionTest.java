package br.ufpr.inf.gres.sentinel.base.solution;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class SolutionTest {

    public SolutionTest() {
    }

    @Test
    public void testCloneConstructor() {
        Solution solution = new Solution();

        Mutant mutant = new Mutant("Mutant1", new File("File1"), new Program("Program1", "Program/path"));
        Mutant mutant2 = new Mutant("Mutant2", new File("File2"), new Program("Program1", "Program/path"));
        Mutant mutant3 = new Mutant("Mutant3", new File("File2"), new Program("Program1", "Program/path"));
        Operator operator = new Operator("Operator1", "Type1");
        TestCase testCase = new TestCase("Test1");

        mutant.getKillingTestCases().add(testCase);
        mutant.setOperator(operator);

        mutant2.getKillingTestCases().add(testCase);
        mutant2.setOperator(operator);

        mutant3.getKillingTestCases().add(testCase);
        mutant3.setOperator(operator);

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

        ArrayList<Mutant> solutionMutants = new ArrayList<>(solution.getMutants());
        ArrayList<Mutant> solution2Mutants = new ArrayList<>(solution2.getMutants());
        for (int i = 0; i < solution.getMutants().size(); i++) {
            Mutant temp1 = solutionMutants.get(i);
            Mutant temp2 = solution2Mutants.get(i);
            assertNotSame(temp1, temp2);
            assertNotSame(temp1.getOperator(), temp2.getOperator());
        }

        ArrayList<Operator> solutionOperators = new ArrayList<>(solution.getOperators());
        ArrayList<Operator> solution2Operators = new ArrayList<>(solution2.getOperators());
        for (int i = 0; i < solution.getOperators().size(); i++) {
            Operator temp1 = solutionOperators.get(i);
            Operator temp2 = solution2Operators.get(i);
            assertNotSame(temp1, temp2);

            List<Mutant> generatedMutants1 = new ArrayList<>(temp1.getGeneratedMutants());
            List<Mutant> generatedMutants2 = new ArrayList<>(temp2.getGeneratedMutants());

            assertNotSame(generatedMutants1.get(0), generatedMutants2.get(0));
        }
    }

    @Test
    public void testGetAndSetMutants() {
        LinkedHashSet<Mutant> mutants = new LinkedHashSet<>();
        Solution solution = new Solution();
        solution.setMutants(mutants);
        assertEquals(mutants, solution.getMutants());
    }

    @Test
    public void testGetAndSetOperators() {
        LinkedHashSet<Operator> operators = new LinkedHashSet<>();
        Solution solution = new Solution();
        solution.setOperators(operators);
        assertEquals(operators, solution.getOperators());
    }

}
