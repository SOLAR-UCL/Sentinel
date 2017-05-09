package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.Multimap;
import java.util.Collection;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class AverageCPUTime<T> extends ObjectiveFunction<T> {

    @Override
    public String getName() {
        return AVERAGE_CPU_TIME;
    }

    @Override
    public Double computeFitness(Solution<T> solution) {
        if (solution.getAttribute("CPUTimes") != null && solution.getAttribute("ConventionalCPUTimes") != null) {
            Multimap<Program, Long> allConventionalCpuTimes = (Multimap<Program, Long>) solution.getAttribute("ConventionalCPUTimes");
            Multimap<Program, Long> allCpuTimes = (Multimap<Program, Long>) solution.getAttribute("CPUTimes");
            if (!allCpuTimes.isEmpty()) {
                for (Program program : allCpuTimes.keySet()) {
                    Collection<Long> conventionalCpuTimes = allConventionalCpuTimes.get(program);
                    Collection<Long> cpuTimes = allCpuTimes.get(program);
                    if (!cpuTimes.isEmpty() && cpuTimes.stream().noneMatch(time -> time.equals(0L))) {
                        double cpuTime = cpuTimes.stream()
                                .mapToLong(Long::longValue)
                                .average()
                                .getAsDouble();

                        double conventionalCpuTime = conventionalCpuTimes.stream()
                                .mapToLong(Long::longValue)
                                .average()
                                .getAsDouble();

                        return cpuTime / conventionalCpuTime;
                    }
                }
            }
        }
        return getWorstValue();
    }

}
