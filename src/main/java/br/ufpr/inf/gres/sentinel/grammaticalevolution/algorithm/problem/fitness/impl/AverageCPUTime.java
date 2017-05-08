package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import java.util.List;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageCPUTime implements ObjectiveFunction<Integer> {

    @Override
    public String getName() {
        return AVERAGE_CPU_TIME;
    }

    @Override
    public Double computeFitness(Solution<Integer> solution) {
        List<Long> cpuTimes = (List<Long>) solution.getAttribute("CPUTimes");
        double cpuTime = cpuTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(getWorstValue());

        List<Long> conventionalCpuTimes = (List<Long>) solution.getAttribute("ConventionalCPUTimes");
        double conventionalCpuTime = conventionalCpuTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(getWorstValue());

        return cpuTime / conventionalCpuTime;
    }

}
