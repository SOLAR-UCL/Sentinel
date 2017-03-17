package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.pitest.classpath.ClassPath;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.config.SettingsFactory;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.testapi.TestGroupConfig;
import org.pitest.util.Glob;

/**
 *
 * @author Giovani Guizzo
 */
public class PITFacade extends IntegrationFacade {

    private String trainingDircetory;

    public PITFacade(String trainingDircetory) {
        this.trainingDircetory = trainingDircetory;
    }

    @Override
    public List<Operator> getAllOperators() {
        List<Operator> allOperators = new ArrayList<>();
        allOperators.add(new Operator("CONDITIONALS_BOUNDARY", "Conditionals"));
        allOperators.add(new Operator("NEGATE_CONDITIONALS", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_EQ_IF", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_EQ_ELSE", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_ORD_IF", "Conditionals"));
        allOperators.add(new Operator("REMOVE_CONDITIONALS_ORD_ELSE", "Conditionals"));
        allOperators.add(new Operator("MATH", "Variables"));
        allOperators.add(new Operator("INCREMENTS", "Variables"));
        allOperators.add(new Operator("INVERT_NEGS", "Variables"));
        allOperators.add(new Operator("INLINE_CONSTS", "Variables"));
        allOperators.add(new Operator("EXPERIMENTAL_MEMBER_VARIABLE", "Variables"));
        allOperators.add(new Operator("RETURN_VALS", "Method"));
        allOperators.add(new Operator("VOID_METHOD_CALLS", "Method"));
        allOperators.add(new Operator("NON_VOID_METHOD_CALLS", "Method"));
        allOperators.add(new Operator("CONSTRUCTOR_CALLS", "Method"));
        allOperators.add(new Operator("EXPERIMENTAL_SWITCH", "Method"));
        allOperators.add(new Operator("EXPERIMENTAL_ARGUMENT_PROPAGATION", "Method"));
        return allOperators;
    }

    @Override
    public List<Mutant> executeOperator(Operator operator) {
        return executeOperators(Lists.newArrayList(operator));
    }

    @Override
    public List<Mutant> executeOperators(List<Operator> operators) {
        Preconditions.checkArgument(operators != null && !operators.isEmpty(), "You must inform at least one operator.");
        List<Mutant> mutants = new ArrayList<>();
        PluginServices plugins = PluginServices.makeForContextLoader();

        ReportOptions reportOptions = createDefaultReportOptions();
        reportOptions.setMutators(operators.stream().map(Operator::getName).collect(Collectors.toList()));

        EntryPoint entryPoint = new EntryPoint();
        AnalysisResult result = entryPoint.execute(new File(trainingDircetory), reportOptions, new SettingsFactory(reportOptions, plugins), new HashMap<>());
        if (result.getError().hasSome()) {
            try {
                throw result.getError().value();
            } catch (Exception ex) {
                Logger.getLogger(PITFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mutants;
    }

    public static void main(String[] args) {
        PITFacade facade = new PITFacade("training");
        IntegrationFacade.setIntegrationFacade(facade);
        IntegrationFacade.setProgramUnderTest(new Program("br.ufpr.inf.gres.TriTyp", new File("training/br/ufpr/inf/gres/TriTyp.java")));
        facade.executeOperators(Lists.newArrayList(new Operator("ALL", "Conditionals")));
    }

    @Override
    public void executeMutant(Mutant mutantToExecute) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public void executeMutants(List<Mutant> mutantsToExecute) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement it
    }

    @Override
    public Mutant combineMutants(List<Mutant> mutantsToCombine) {
        throw new UnsupportedOperationException("Sorry, PIT test is not adapted to work with HOMs. Please, select a grammar file without HOMs.");
    }

    @Override
    public Program instantiateProgram(String programName) {
        String replace = programName.replace(".java", "");
        replace = CharMatcher.anyOf("\\/.").replaceFrom(replace, File.separator);
        return new Program(programName, new File(replace + ".java"));
    }

    private ReportOptions createDefaultReportOptions() {
        ReportOptions reportOptions = new ReportOptions();
        String absolutePath = new File(trainingDircetory).getAbsolutePath();
        reportOptions.setReportDir(absolutePath + File.separator + "result");
        reportOptions.setGroupConfig(new TestGroupConfig());
        reportOptions.setSourceDirs(Lists.newArrayList(new File(absolutePath)));
        reportOptions.setCodePaths(Lists.newArrayList(absolutePath));
        reportOptions.setTargetClasses(Lists.newArrayList(new Glob(IntegrationFacade.getProgramUnderTest().getFullName())));
        reportOptions.setTargetTests(Lists.newArrayList(new Glob(IntegrationFacade.getProgramUnderTest().getFullName() + "Test")));
        reportOptions.setClassPathElements(Lists.newArrayList(ClassPath.getClassPathElementsAsPaths()));
        reportOptions.getClassPathElements().add(absolutePath);
        reportOptions.addOutputFormats(Lists.newArrayList("CSV"));
        reportOptions.setMutateStaticInitializers(true);
        reportOptions.setDetectInlinedCode(true);
        reportOptions.setShouldCreateTimestampedReports(true);
        reportOptions.setIncludeLaunchClasspath(true);
        return reportOptions;
    }

}
