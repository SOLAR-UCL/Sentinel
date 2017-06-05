package br.ufpr.inf.gres.sentinel.integration.cache;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Giovani Guizzo
 */
public class FacadeCache {

    private Map<Program, Boolean> cached = new HashMap<>();
    private String outputDirectory;
    private String inputDirectory;

    private HashMap<Program, ListMultimap<Operator, Long>> operatorsCPUTime;
    private HashMap<Program, ListMultimap<Operator, Long>> operatorsExecutionTime;
    private HashMap<Program, ListMultimap<Mutant, Long>> mutantsCPUTime;
    private HashMap<Program, ListMultimap<Mutant, Long>> mutantsExecutionTime;

    public FacadeCache() {
    }

    public FacadeCache(String inputDirectory, String outputDirectory) {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
    }

    public boolean isCached(Program program) {
        return cached.computeIfAbsent(program, tempProgram -> false);
    }

    public void setCached(Program program) {
        cached.put(program, true);
    }

    public void clearCache(Program program) {
        this.cached.remove(program);
    }

    public void recordOperatorCPUTime(Program program, Operator operator, long cpuTime) {
        ListMultimap<Operator, Long> times = operatorsCPUTime.computeIfAbsent(program, tempProgram -> ArrayListMultimap.create());
        times.put(operator, cpuTime);
    }

    public void recordOperatorExecutionTime(Program program, Operator operator, long executionTime) {
        ListMultimap<Operator, Long> times = operatorsExecutionTime.computeIfAbsent(program, tempProgram -> ArrayListMultimap.create());
        times.put(operator, executionTime);
    }

    public void recordMutantCPUTime(Program program, Mutant mutant, long cpuTime) {
        ListMultimap<Mutant, Long> times = mutantsCPUTime.computeIfAbsent(program, tempProgram -> ArrayListMultimap.create());
        times.put(mutant, cpuTime);
    }

    public void recordMutantExecutionTime(Program program, Mutant mutant, long executionTime) {
        ListMultimap<Mutant, Long> times = mutantsExecutionTime.computeIfAbsent(program, tempProgram -> ArrayListMultimap.create());
        times.put(mutant, executionTime);
    }

    public void writeCache() {
        //TODO implement it
    }

}
