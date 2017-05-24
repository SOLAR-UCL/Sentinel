package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.fitness.ObjectiveFunction;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Set;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 * @param <T>
 */
public class AverageCPUTime<T> extends ObjectiveFunction<T> {

    /**
     *
     * @param solution
     * @return
     */
    @Override
    public Double computeFitness(Solution<T> solution) {
        if (solution.getAttribute("CPUTimes") != null && solution.getAttribute("ConventionalCPUTimes") != null) {
            Multimap<Program, Long> allConventionalCpuTimes = (Multimap<Program, Long>) solution.getAttribute("ConventionalCPUTimes");
            Multimap<Program, Long> allCpuTimes = (Multimap<Program, Long>) solution.getAttribute("CPUTimes");
            double cpuSum = 0.0;
            Set<Program> allPrograms = allConventionalCpuTimes.keySet();
            for (Program program : allPrograms) {
                Collection<Long> conventionalCpuTimes = allConventionalCpuTimes.get(program);
                Collection<Long> cpuTimes = allCpuTimes.get(program);
                double cpuTime = cpuTimes.stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .orElse(0.0);

                double conventionalCpuTime = conventionalCpuTimes.stream()
                        .mapToLong(Long::longValue)
                        .average()
                        .getAsDouble();

                cpuSum += cpuTime / (conventionalCpuTime == 0 ? 1 : conventionalCpuTime);
            }
            return cpuSum / allPrograms.size();
        }
        return this.getWorstValue();
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return AVERAGE_CPU_TIME;
    }

}
