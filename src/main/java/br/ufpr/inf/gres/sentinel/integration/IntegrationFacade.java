package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Giovani Guizzo
 */
public abstract class IntegrationFacade {

    private static IntegrationFacade FACADE_INSTANCE;

    /**
     *
     * @return
     */
    public static IntegrationFacade getIntegrationFacade() {
        return FACADE_INSTANCE;
    }

    /**
     *
     * @param facade
     */
    public static void setIntegrationFacade(IntegrationFacade facade) {
        FACADE_INSTANCE = facade;
    }

    protected final ArrayListMultimap<Program, Long> conventionalExecutionCPUTimes = ArrayListMultimap.create();
    protected final ArrayListMultimap<Program, Long> conventionalExecutionTimes = ArrayListMultimap.create();
    protected final ArrayListMultimap<Program, Mutant> conventionalMutants = ArrayListMultimap.create();
    protected final String inputDirectory;

    public IntegrationFacade(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    /**
     *
     * @param mutantToExecute
     */
    public abstract void executeMutant(Mutant mutantToExecute, Program program);

    /**
     *
     * @param mutantsToExecute
     */
    public abstract void executeMutants(List<Mutant> mutantsToExecute, Program program);

    /**
     *
     * @param operator
     * @return
     */
    public abstract List<Mutant> executeOperator(Operator operator, Program program);

    /**
     *
     * @param operators
     * @return
     */
    public abstract List<Mutant> executeOperators(List<Operator> operators, Program program);

    /**
     *
     * @return
     */
    public abstract List<Operator> getAllOperators();

    /**
     *
     * @return
     */
    public ListMultimap<Program, Long> getConventionalExecutionCPUTimes() {
        return Multimaps.unmodifiableListMultimap(this.conventionalExecutionCPUTimes);
    }

    /**
     *
     * @return
     */
    public ListMultimap<Program, Long> getConventionalExecutionTimes() {
        return Multimaps.unmodifiableListMultimap(this.conventionalExecutionTimes);
    }

    /**
     *
     * @return
     */
    public ListMultimap<Program, Mutant> getConventionalMutants() {
        return Multimaps.unmodifiableListMultimap(this.conventionalMutants);
    }

    /**
     *
     * @param program
     * @param repetitions
     */
    public synchronized void initializeConventionalStrategy(Program program, int repetitions) {
        if (!this.conventionalExecutionCPUTimes.containsKey(program)) {
            this.runConventionalStrategy(program, 1);
            this.conventionalExecutionCPUTimes.removeAll(program);
            this.conventionalExecutionTimes.removeAll(program);
            this.conventionalMutants.removeAll(program);
            this.runConventionalStrategy(program, repetitions);
        }
    }

    /**
     *
     * @param programString
     * @return
     */
    public Program instantiateProgram(String programString) {
        String errorMessage = "Something went wrong with the following program String: " + programString + ". It appears that it does not have enough information for the mutation testing. If you need more help, please reffer to the '-h' argument.";

        Iterator<String> split = Splitter.on(";").trimResults().split(programString).iterator();

        Preconditions.checkArgument(split.hasNext(), errorMessage);
        String name = split.next();

        Preconditions.checkArgument(split.hasNext(), errorMessage);
        String sourceDir = split.next();

        Preconditions.checkArgument(split.hasNext(), errorMessage);
        String targetClassesGlob = split.next();

        Preconditions.checkArgument(split.hasNext(), errorMessage);
        String targetTestsGlob = split.next();

        List<String> classPath = new ArrayList<>();
        while (split.hasNext()) {
            classPath.add(this.inputDirectory + File.separator + split.next());
        }

        final Program program = new Program(name, this.inputDirectory + File.separator + sourceDir);
        program.putAttribute("targetClassesGlob", targetClassesGlob);
        program.putAttribute("targetTestsGlob", targetTestsGlob);
        program.putAttribute("classPath", classPath);
        return program;
    }

    /**
     *
     * @param programsStrings
     * @return
     */
    public List<Program> instantiatePrograms(List<String> programsStrings) {
        List<Program> programs = new ArrayList<>();
        for (String programName : programsStrings) {
            programs.add(this.instantiateProgram(programName));
        }
        return programs;
    }

    /**
     *
     * @param program
     * @param repetitions
     */
    protected void runConventionalStrategy(Program program, int repetitions) {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        List<Mutant> allMutants = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            long currentThreadCpuTime = threadBean.getCurrentThreadCpuTime();
            List<Operator> operators = this.getAllOperators();
            allMutants = this.executeOperators(operators, program);
            this.executeMutants(allMutants, program);
            currentThreadCpuTime = threadBean.getCurrentThreadCpuTime() - currentThreadCpuTime;
            stopwatch.stop();
            this.conventionalExecutionCPUTimes.put(program, currentThreadCpuTime);
            this.conventionalExecutionTimes.put(program, stopwatch.elapsed(TimeUnit.NANOSECONDS));
        }
        this.conventionalMutants.putAll(program, allMutants);
    }

}
