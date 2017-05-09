package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import java.io.File;
import org.junit.Test;
import org.uma.jmetal.util.point.util.PointSolution;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageCPUTimeTest {

    public AverageCPUTimeTest() {
    }

    @Test
    public void testGetName() {
        assertEquals(ObjectiveFunction.AVERAGE_CPU_TIME, new AverageCPUTime().getName());
    }

    @Test
    public void testCompute() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Long> cpuTimes = ArrayListMultimap.create();
        cpuTimes.putAll(program, Lists.newArrayList(100L, 50L, 30L, 10L));
        pointSolution.setAttribute("CPUTimes", cpuTimes);

        ArrayListMultimap<Program, Long> conventionalCPUTimes = ArrayListMultimap.create();
        conventionalCPUTimes.putAll(program, Lists.newArrayList(100L, 50L, 30L, 10L));
        pointSolution.setAttribute("ConventionalCPUTimes", conventionalCPUTimes);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(1.0, averageCPUTime.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute2() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Long> cpuTimes = ArrayListMultimap.create();
        cpuTimes.putAll(program, Lists.newArrayList(100L, 50L, 30L, 10L));
        pointSolution.setAttribute("CPUTimes", cpuTimes);

        ArrayListMultimap<Program, Long> conventionalCPUTimes = ArrayListMultimap.create();
        conventionalCPUTimes.putAll(program, Lists.newArrayList(100L, 100L, 100L, 100L));
        pointSolution.setAttribute("ConventionalCPUTimes", conventionalCPUTimes);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(0.475, averageCPUTime.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute3() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Long> cpuTimes = ArrayListMultimap.create();
        cpuTimes.putAll(program, Lists.newArrayList(0L, 10L));
        pointSolution.setAttribute("CPUTimes", cpuTimes);

        ArrayListMultimap<Program, Long> conventionalCPUTimes = ArrayListMultimap.create();
        conventionalCPUTimes.putAll(program, Lists.newArrayList(100L, 100L, 100L, 100L));
        pointSolution.setAttribute("ConventionalCPUTimes", conventionalCPUTimes);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(averageCPUTime.getWorstValue(), averageCPUTime.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute4() {
        PointSolution pointSolution = new PointSolution(1);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(averageCPUTime.getWorstValue(), averageCPUTime.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute5() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Long> cpuTimes = ArrayListMultimap.create();
        cpuTimes.putAll(program, Lists.newArrayList(100L));
        pointSolution.setAttribute("CPUTimes", cpuTimes);

        ArrayListMultimap<Program, Long> conventionalCPUTimes = ArrayListMultimap.create();
        conventionalCPUTimes.putAll(program, Lists.newArrayList(50L));
        pointSolution.setAttribute("ConventionalCPUTimes", conventionalCPUTimes);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(2.0, averageCPUTime.computeFitness(pointSolution), 0.01);
    }

    @Test
    public void testCompute6() {
        Program program = new Program("Program1", new File("Program1"));
        PointSolution pointSolution = new PointSolution(1);

        ArrayListMultimap<Program, Long> cpuTimes = ArrayListMultimap.create();
        pointSolution.setAttribute("CPUTimes", cpuTimes);

        ArrayListMultimap<Program, Long> conventionalCPUTimes = ArrayListMultimap.create();
        conventionalCPUTimes.putAll(program, Lists.newArrayList(100L, 100L, 100L, 100L));
        pointSolution.setAttribute("ConventionalCPUTimes", conventionalCPUTimes);

        AverageCPUTime averageCPUTime = new AverageCPUTime();
        assertEquals(averageCPUTime.getWorstValue(), averageCPUTime.computeFitness(pointSolution), 0.01);
    }

}
