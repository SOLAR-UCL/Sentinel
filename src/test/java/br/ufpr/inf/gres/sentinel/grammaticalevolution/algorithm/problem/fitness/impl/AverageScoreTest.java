package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collection;
import org.junit.Test;
import org.uma.jmetal.util.point.util.PointSolution;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageScoreTest {

    public AverageScoreTest() {
    }

    @Test
    public void testCompute() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant1, mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-0.6, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute2() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant1, mutant3));
        mutants.put(program, Lists.newArrayList(mutant2));
        mutants.put(program, Lists.newArrayList(mutant2));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-0.3333, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute3() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant4, mutant2));
        mutants.put(program, Lists.newArrayList(mutant5, mutant2));
        mutants.put(program, Lists.newArrayList(mutant1));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-0.8, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute4() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant4, mutant2));
        mutants.put(program, Lists.newArrayList(mutant5, mutant1, mutant2));
        mutants.put(program, Lists.newArrayList(mutant4, mutant2));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-1.0, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute5() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList());
        mutants.put(program, Lists.newArrayList());
        mutants.put(program, Lists.newArrayList());
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(0.0, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute6() {
        PointSolution pointSolution = new PointSolution(1);

        AverageScore averageScore = new AverageScore();
        assertEquals(averageScore.getWorstValue(), averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute7() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        Mutant mutant6 = new Mutant("Mutant6", new File("Mutant5"), program);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);
        conventionalMutants.put(program, mutant6);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant1, mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-0.6, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testCompute8() {
        Program program = new Program("Program1", new File("Program1"));
        Program program2 = new Program("Program2", new File("Program2"));
        PointSolution pointSolution = new PointSolution(1);

        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", new File("Mutant1"), program);
        mutant1.getKillingTestCases().add(testCase1);

        Mutant mutant2 = new Mutant("Mutant2", new File("Mutant2"), program);
        mutant2.getKillingTestCases().add(testCase2);

        Mutant mutant3 = new Mutant("Mutant3", new File("Mutant3"), program);
        mutant3.getKillingTestCases().add(testCase3);
        mutant3.getKillingTestCases().add(testCase1);

        Mutant mutant4 = new Mutant("Mutant4", new File("Mutant4"), program);
        mutant4.getKillingTestCases().add(testCase1);
        mutant4.getKillingTestCases().add(testCase4);

        Mutant mutant5 = new Mutant("Mutant5", new File("Mutant5"), program);
        mutant5.getKillingTestCases().add(testCase4);
        mutant5.getKillingTestCases().add(testCase5);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.put(program, mutant1);
        conventionalMutants.put(program, mutant2);
        conventionalMutants.put(program, mutant3);
        conventionalMutants.put(program, mutant4);
        conventionalMutants.put(program, mutant5);

        conventionalMutants.put(program2, mutant1);
        conventionalMutants.put(program2, mutant2);
        conventionalMutants.put(program2, mutant3);
        conventionalMutants.put(program2, mutant4);
        conventionalMutants.put(program2, mutant5);
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(mutant1, mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));
        mutants.put(program, Lists.newArrayList(mutant3));

        mutants.put(program2, Lists.newArrayList(mutant5));
        mutants.put(program2, Lists.newArrayList(mutant3));
        mutants.put(program2, Lists.newArrayList(mutant3));
        pointSolution.setAttribute("Mutants", mutants);

        AverageScore averageScore = new AverageScore();
        assertEquals(-0.5666, averageScore.computeFitness(pointSolution), 0.1);
    }

    @Test
    public void testGetName() {
        assertEquals(ObjectiveFunction.AVERAGE_SCORE, new AverageScore().getName());
    }

}
