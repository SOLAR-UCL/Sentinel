package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pitest.mutationtest.MutationResult;
import org.pitest.mutationtest.build.MutationAnalysisUnit;
import org.pitest.mutationtest.build.MutationTestUnit;
import org.pitest.mutationtest.commandline.OptionsParser;
import org.pitest.mutationtest.commandline.ParseResult;
import org.pitest.mutationtest.commandline.PluginFilter;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.config.SettingsFactory;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator;
import org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator;

/**
 *
 * @author Giovani Guizzo
 */
public class PITFacade extends IntegrationFacade {

    private static final Logger LOGGER = LogManager.getLogger(PITFacade.class);

    protected static final ArrayList<Operator> ALL_OPERATORS = new ArrayList<>();
    protected static final HashMap<String, Operator> ALL_OPERATORS_BY_CLASS = new HashMap<>();

    static {
        Operator operator;

        operator = new Operator("CONDITIONALS_BOUNDARY", "Conditionals");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR.getGloballyUniqueId(), operator);
//
        operator = new Operator("NEGATE_CONDITIONALS", "Conditionals");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR.getGloballyUniqueId(), operator);
//
//        operator = new Operator("REMOVE_CONDITIONALS_EQ_IF", "Conditionals");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new RemoveConditionalMutator(RemoveConditionalMutator.Choice.EQUAL, true).getGloballyUniqueId(), operator);
//
//        operator = new Operator("REMOVE_CONDITIONALS_EQ_ELSE", "Conditionals");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new RemoveConditionalMutator(RemoveConditionalMutator.Choice.EQUAL, false).getGloballyUniqueId(), operator);
//
//        operator = new Operator("REMOVE_CONDITIONALS_ORD_IF", "Conditionals");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new RemoveConditionalMutator(RemoveConditionalMutator.Choice.ORDER, true).getGloballyUniqueId(), operator);
//
//        operator = new Operator("REMOVE_CONDITIONALS_ORD_ELSE", "Conditionals");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new RemoveConditionalMutator(RemoveConditionalMutator.Choice.ORDER, false).getGloballyUniqueId(), operator);
//
        operator = new Operator("MATH", "Variables");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(MathMutator.MATH_MUTATOR.getGloballyUniqueId(), operator);
//
        operator = new Operator("INCREMENTS", "Variables");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(IncrementsMutator.INCREMENTS_MUTATOR.getGloballyUniqueId(), operator);
//
        operator = new Operator("INVERT_NEGS", "Variables");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(InvertNegsMutator.INVERT_NEGS_MUTATOR.getGloballyUniqueId(), operator);
//
//        operator = new Operator("INLINE_CONSTS", "Variables");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new InlineConstantMutator().getGloballyUniqueId(), operator);
//
//        operator = new Operator("EXPERIMENTAL_MEMBER_VARIABLE", "Variables");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator().getGloballyUniqueId(), operator);
//
        operator = new Operator("RETURN_VALS", "Method");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(ReturnValsMutator.RETURN_VALS_MUTATOR.getGloballyUniqueId(), operator);
//
        operator = new Operator("VOID_METHOD_CALLS", "Method");
        ALL_OPERATORS.add(operator);
        ALL_OPERATORS_BY_CLASS.put(VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR.getGloballyUniqueId(), operator);
//
//        operator = new Operator("NON_VOID_METHOD_CALLS", "Method");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(NonVoidMethodCallMutator.NON_VOID_METHOD_CALL_MUTATOR.getGloballyUniqueId(), operator);
//
//        operator = new Operator("CONSTRUCTOR_CALLS", "Method");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(ConstructorCallMutator.CONSTRUCTOR_CALL_MUTATOR.getGloballyUniqueId(), operator);
//
//        operator = new Operator("EXPERIMENTAL_SWITCH", "Method");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(new org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator().getGloballyUniqueId(), operator);
//
//        operator = new Operator("EXPERIMENTAL_ARGUMENT_PROPAGATION", "Method");
//        ALL_OPERATORS.add(operator);
//        ALL_OPERATORS_BY_CLASS.put(ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR.getGloballyUniqueId(), operator);
    }
    protected final HashMap<Program, EntryPointImpl> entryPoints = new HashMap<>();
    protected final HashMap<Program, HashMap<Mutant, MutationDetails>> generatedMutants = new HashMap<>();
    protected final HashMap<Program, HashMap<MutationTestUnit, Set<Mutant>>> unitToMutants = new HashMap<>();

    /**
     *
     * @param inputDirectory
     */
    public PITFacade(String inputDirectory) {
        super(inputDirectory);
    }

    /**
     *
     * @param mutantToExecute
     * @param program
     */
    @Override
    public void executeMutant(Mutant mutantToExecute, Program program) {
        if (mutantToExecute != null) {
            this.executeMutants(Lists.newArrayList(mutantToExecute), program);
        }
    }

    private ReportOptions createDefaultReportOptions(PluginServices plugins, Program programUnderTest) {
        String trainingDir = new File(this.inputDirectory).getAbsolutePath();

        List<String> programClassPath = (List<String>) programUnderTest.getAttribute("classPath");

        String[] args = new String[]{
            "--targetClasses", (String) programUnderTest.getAttribute("targetClassesGlob"),
            "--targetTests", (String) programUnderTest.getAttribute("targetTestsGlob"),
            "--sourceDirs", programUnderTest.getSourceFile().getAbsolutePath(),
            "--reportDir", trainingDir,
            //"--threads", "14",
            "--outputFormats", "CSV",
            "--classPath", Joiner.on(",").join(programClassPath)
        };

        final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));
        final ParseResult pr = parser.parse(args);
        final ReportOptions reportOptions = pr.getOptions();

        reportOptions.setMutateStaticInitializers(true);
        reportOptions.setDetectInlinedCode(true);
        reportOptions.setShouldCreateTimestampedReports(false);
        reportOptions.setIncludeLaunchClasspath(true);
        reportOptions.setVerbose(false);

        return reportOptions;
    }

    /**
     *
     * @param mutantsToExecute
     * @param program
     */
    @Override
    public void executeMutants(List<Mutant> mutantsToExecute, Program program) {
        if (mutantsToExecute != null && !mutantsToExecute.isEmpty()) {
            LOGGER.debug("Preparing to execute " + mutantsToExecute.size() + " mutants for program " + program.getName() + ".");
            PluginServices plugins = PluginServices.makeForContextLoader();

            ReportOptions reportOptions = this.createDefaultReportOptions(plugins, program);

            try {
                HashMap<Mutant, MutationDetails> descriptions = this.generatedMutants.get(program);
                HashMap<MutationTestUnit, Set<Mutant>> allUnitsMutants = unitToMutants.get(program);
                if (allUnitsMutants != null || allUnitsMutants.isEmpty()) {
                    for (Map.Entry<MutationTestUnit, Set<Mutant>> unitEntry : allUnitsMutants.entrySet()) {
                        MutationTestUnit unit = unitEntry.getKey();
                        Set<Mutant> unitMutants = unitEntry.getValue();

                        Field field = unit.getClass().getDeclaredField("availableMutations");
                        field.setAccessible(true);
                        Collection<MutationDetails> fieldValue = (Collection<MutationDetails>) field.get(unit);
                        fieldValue.clear();
                        field.set(unit, fieldValue);

                        for (Mutant mutant : mutantsToExecute) {
                            if (unitMutants.contains(mutant)) {
                                MutationDetails description = descriptions.get(mutant);
                                fieldValue.add(description);
                            }
                        }

                        field.set(unit, fieldValue);
                    }
                    LOGGER.debug("Starting mutants execution.");
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    EntryPointImpl entryPoint = this.getOrCreateEntryPoint(program);
                    Collection<MutationResult> result = entryPoint.executeMutants(null, reportOptions, new SettingsFactory(reportOptions, plugins), new HashMap<>(), new ArrayList<>(allUnitsMutants.keySet()));
                    stopwatch.stop();
                    LOGGER.debug("Mutants execution finished in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));

                    for (Mutant mutant : mutantsToExecute) {
                        MutationDetails description = descriptions.get(mutant);
                        MutationResult specificResult = Iterables.find(result, (tempItem) -> {
                            return tempItem.getDetails().equals(description);
                        });
                        mutant.getKillingTestCases().addAll(specificResult.getKillingTest().map((testCase) -> {
                            TestCase sentinelTestCase = new TestCase(testCase);
                            return sentinelTestCase;
                        }));
                    }
                    LOGGER.debug("Mutants dead: " + mutantsToExecute.stream().filter(Mutant::isDead).count());
                    LOGGER.debug("Mutants alive: " + mutantsToExecute.stream().filter(Mutant::isAlive).count());
                } else {
                    IOException ex = new IOException("Something went wrong. I could not find the mutation unit for the mutation procedure. This has something to do to the program under test. Maybe it is non-existent?");
                    LOGGER.fatal(ex.getMessage(), ex);
                    throw ex;
                }
            } catch (IOException | SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException ex) {
                LOGGER.fatal(ex.getMessage(), ex);
            }
        }
    }

    /**
     *
     * @param operator
     * @param program
     * @return
     */
    @Override
    public List<Mutant> executeOperator(Operator operator, Program program) {
        return this.executeOperators(Lists.newArrayList(operator), program);
    }

    /**
     *
     * @param operators
     * @param program
     * @return
     */
    @Override
    public List<Mutant> executeOperators(List<Operator> operators, Program program) {
        List<Mutant> mutants = new ArrayList<>();
        if (operators != null && !operators.isEmpty()) {
            LOGGER.debug("Preparing to execute " + operators.size() + " operators for program " + program.getName() + ".");
            LOGGER.trace("Operators: " + operators.toString());
            final PluginServices plugins = PluginServices.makeForContextLoader();
            ReportOptions reportOptions = this.createDefaultReportOptions(plugins, program);
            reportOptions.setMutators(operators.stream().map(Operator::getName).collect(Collectors.toList()));
            try {
                LOGGER.debug("Starting operators execution.");
                Stopwatch stopwatch = Stopwatch.createStarted();
                EntryPointImpl entryPoint = this.getOrCreateEntryPoint(program);
                List<MutationAnalysisUnit> units = entryPoint.generateMutants(null, reportOptions, new SettingsFactory(reportOptions, plugins), new HashMap<>());
                stopwatch.stop();
                LOGGER.debug("Operators execution finished in " + DurationFormatUtils.formatDurationHMS(stopwatch.elapsed(TimeUnit.MILLISECONDS)));

                HashMap<MutationTestUnit, Set<Mutant>> unitToMutant = this.unitToMutants.computeIfAbsent(program, t -> new HashMap<>());
                boolean initializing = unitToMutant.isEmpty();
                for (MutationAnalysisUnit unit : units) {
                    if (unit instanceof MutationTestUnit) {
                        MutationTestUnit testUnit = (MutationTestUnit) unit;
                        Set<Mutant> unitMutants = new HashSet<>();
                        if (initializing) {
                            unitToMutant.put(testUnit, unitMutants);
                        }
                        Field field = testUnit.getClass().getDeclaredField("availableMutations");
                        field.setAccessible(true);
                        Collection<MutationDetails> fieldValue = (Collection<MutationDetails>) field.get(testUnit);
                        mutants.addAll(fieldValue.stream().map((mutationDetails) -> {
                            Mutant mutant = new Mutant(mutationDetails.getId().toString(), null, program);
                            Operator mappedOperator = ALL_OPERATORS_BY_CLASS.get(mutationDetails.getMutator());
                            Operator operator = Iterables.find(operators, (tempOperator) -> tempOperator.equals(mappedOperator));
                            mutant.setOperator(operator);
                            operator.getGeneratedMutants().add(mutant);

                            HashMap<Mutant, MutationDetails> programMutants = this.generatedMutants.computeIfAbsent(program, t -> new HashMap<>());
                            programMutants.putIfAbsent(mutant, mutationDetails);

                            unitMutants.add(mutant);
                            return mutant;
                        }).collect(Collectors.toList()));
                    } else {
                        IllegalArgumentException ex = new IllegalArgumentException("This should not be happening!");
                        LOGGER.fatal(ex.getMessage(), ex);
                        throw ex;
                    }
                }
                LOGGER.debug(mutants.size() + " mutants generated.");
            } catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                LOGGER.fatal(ex.getMessage(), ex);
            }
        }
        return mutants;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Operator> getAllOperators() {
        ArrayList<Operator> operators = new ArrayList<>();
        for (Operator operator : ALL_OPERATORS) {
            operators.add(new Operator(operator));
        }
        return operators;
    }

    private EntryPointImpl getOrCreateEntryPoint(Program program) {
        return this.entryPoints.computeIfAbsent(program, (programTemp) -> {
            return new EntryPointImpl();
        });
    }

}
