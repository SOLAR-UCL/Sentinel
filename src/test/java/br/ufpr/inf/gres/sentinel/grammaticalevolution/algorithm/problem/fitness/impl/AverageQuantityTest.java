package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.uma.jmetal.util.point.util.PointSolution;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageQuantityTest {

    public AverageQuantityTest() {
    }

    @Test
    public void testGetName() {
        assertEquals(ObjectiveFunction.AVERAGE_QUANTITY, new AverageQuantity().getName());
    }

    @Test
    public void testCompute() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, Lists.newArrayList(
                new Mutant("Mutant1", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant3", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant4", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        mutants.put(program, Lists.newArrayList(
                new Mutant("Mutant1", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        mutants.put(program, Lists.newArrayList(
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        pointSolution.setAttribute("Mutants", mutants);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.putAll(program, Lists.newArrayList(
                new Mutant("Mutant1", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant3", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant4", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        AverageQuantity averageQuantity = new AverageQuantity();
        assertEquals(0.66666, averageQuantity.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute2() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        mutants.put(program, new ArrayList());
        mutants.put(program, new ArrayList());
        mutants.put(program, new ArrayList());
        pointSolution.setAttribute("Mutants", mutants);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.putAll(program, Lists.newArrayList(
                new Mutant("Mutant1", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant3", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant4", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        AverageQuantity averageQuantity = new AverageQuantity();
        assertEquals(averageQuantity.getWorstValue(), averageQuantity.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute3() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Collection<Mutant>> mutants = ArrayListMultimap.create();
        pointSolution.setAttribute("Mutants", mutants);

        ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
        conventionalMutants.putAll(program, Lists.newArrayList(
                new Mutant("Mutant1", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant2", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant3", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant4", new File("Mutant1"), new Program("Program1", new File("Program1"))),
                new Mutant("Mutant5", new File("Mutant1"), new Program("Program1", new File("Program1")))));
        pointSolution.setAttribute("ConventionalMutants", conventionalMutants);

        AverageQuantity averageQuantity = new AverageQuantity();
        assertEquals(averageQuantity.getWorstValue(), averageQuantity.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute4() {
        PointSolution pointSolution = new PointSolution(1);

        AverageQuantity averageQuantity = new AverageQuantity();
        assertEquals(averageQuantity.getWorstValue(), averageQuantity.computeFitness(pointSolution), 0.01);
    }

}
