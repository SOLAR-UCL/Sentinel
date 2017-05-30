package br.ufpr.inf.gres.sentinel.integration.pit;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.pitest.classinfo.ClassByteArraySource;
import org.pitest.classinfo.ClassInfo;
import org.pitest.classinfo.ClassName;
import org.pitest.classinfo.HierarchicalClassId;
import org.pitest.classpath.ClassPathByteArraySource;
import org.pitest.classpath.CodeSource;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.coverage.CoverageGenerator;
import org.pitest.coverage.TestInfo;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.help.Help;
import org.pitest.help.PitHelpError;
import org.pitest.mutationtest.*;
import org.pitest.mutationtest.build.*;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.config.SettingsFactory;
import org.pitest.mutationtest.engine.MutationEngine;
import org.pitest.mutationtest.execute.MutationAnalysisExecutor;
import org.pitest.mutationtest.filter.MutationFilterFactory;
import org.pitest.mutationtest.incremental.DefaultCodeHistory;
import org.pitest.mutationtest.incremental.IncrementalAnalyser;
import org.pitest.mutationtest.tooling.MutationStrategies;
import org.pitest.util.Timings;

/**
 * Shamelessly copied the content of MutationCoverage and changed its behavior because of private access variables.
 *
 * @author Giovani Guizzo
 */
public class MutationCoverageImpl {

    private static final int MB = 1024 * 1024;
    private final File baseDir;

    private final CodeSource code;
    private final CoverageDatabase coverageData;
    private final ReportOptions data;
    private final SettingsFactory settings;
    private final MutationStrategies strategies;
    private final Timings timings;

    /**
     *
     * @param strategies
     * @param baseDir
     * @param code
     * @param data
     * @param settings
     * @param timings
     * @param coverageData
     */
    public MutationCoverageImpl(final MutationStrategies strategies,
            final File baseDir, final CodeSource code, final ReportOptions data,
            final SettingsFactory settings, final Timings timings,
            CoverageDatabase coverageData) {
        this.strategies = strategies;
        this.data = data;
        this.settings = settings;
        this.timings = timings;
        this.code = code;
        this.baseDir = baseDir;
        this.coverageData = coverageData;
        this.verifyBuildSuitableForMutationTesting();
        this.checkExcludedRunners();
        this.history().initialize();
    }

    private List<MutationAnalysisUnit> buildMutationTests(final CoverageDatabase coverageData, final MutationEngine engine) {
        MutationTestBuilder builder = this.getBuilder(engine, coverageData);
        return builder.createMutationTestUnits(this.code.getCodeUnderTestNames());
    }

    private void checkExcludedRunners() {
        Collection<String> excludedRunners = this.data.getExcludedRunners();
        if (!excludedRunners.isEmpty()) {
            // Check whether JUnit4 is available or not
            try {
                Class.forName("org.junit.runner.RunWith");
            } catch (ClassNotFoundException e) {
                // JUnit4 is not available on the classpath
                throw new PitHelpError(Help.NO_JUNIT_EXCLUDE_RUNNERS);
            }
        }
    }

    private void checkMutationsFound(final List<MutationTestUnit> tus) {
        if ((tus == null || tus.isEmpty()) && this.data.shouldFailWhenNoMutations()) {
            throw new PitHelpError(Help.NO_MUTATIONS_FOUND);
        }
    }

    private CoverageGenerator coverage() {
        return this.strategies.coverage();
    }

    /**
     *
     * @return
     */
    public List<MutationAnalysisUnit> createMutants() {
        MutationEngine engine = this.strategies.factory().createEngine(
                this.data.isMutateStaticInitializers(),
                Prelude.or(this.data.getExcludedMethods()),
                this.data.getLoggingClasses(), this.data.getMutators(),
                this.data.isDetectInlinedCode());
        this.timings.registerStart(Timings.Stage.BUILD_MUTATION_TESTS);
        final List<MutationAnalysisUnit> tus = this.buildMutationTests(this.coverageData, engine);
        this.timings.registerEnd(Timings.Stage.BUILD_MUTATION_TESTS);
        return tus;
    }

    private Set<ClassName> getAllClassesAndTests(
            final CoverageDatabase coverageData) {
        final Set<ClassName> names = new HashSet<>();
        for (final ClassName each : this.code.getCodeUnderTestNames()) {
            names.add(each);
            FCollection.mapTo(coverageData.getTestsForClass(each),
                    TestInfo.toDefiningClassName(), names);
        }
        return names;
    }

    /**
     *
     * @param engine
     * @param coverageData1
     * @return
     */
    public MutationTestBuilder getBuilder(final MutationEngine engine, final CoverageDatabase coverageData1) {
        final MutationConfig mutationConfig = new MutationConfig(engine, this.coverage()
                .getLaunchOptions());
        ClassByteArraySource bas = new ClassPathByteArraySource(
                this.data.getClassPath());
        TestPrioritiser testPrioritiser = this.settings.getTestPrioritiser().makeTestPrioritiser(this.data.getFreeFormProperties(), this.code, coverageData1);
        final MutationSource source = new MutationSource(mutationConfig,
                this.makeFilter().createFilter(this.data.getFreeFormProperties(), this.code,
                        this.data.getMaxMutationsPerClass()), testPrioritiser, bas);
        final MutationAnalyser analyser = new IncrementalAnalyser(new DefaultCodeHistory(this.code, this.history()), coverageData1);
        final WorkerFactory wf = new WorkerFactory(this.baseDir, this.coverage()
                .getConfiguration(), mutationConfig,
                new PercentAndConstantTimeoutStrategy(this.data.getTimeoutFactor(),
                        this.data.getTimeoutConstant()), this.data.isVerbose(), this.data
                .getClassPath().getLocalClassPath());
        MutationGrouper grouper = this.settings.getMutationGrouper().makeFactory(
                this.data.getFreeFormProperties(), this.code,
                this.data.getNumberOfThreads(), this.data.getMutationUnitSize());
        final MutationTestBuilder builder = new MutationTestBuilder(wf, analyser,
                source, grouper);
        return builder;
    }

    private HistoryStore history() {
        return this.strategies.history();
    }

    private MutationFilterFactory makeFilter() {
        return this.settings.createMutationFilter();
    }

    private int numberOfThreads() {
        return Math.max(1, this.data.getNumberOfThreads());
    }

    private void recordClassPath(final CoverageDatabase coverageData) {
        final Set<ClassName> allClassNames = this.getAllClassesAndTests(coverageData);
        final Collection<HierarchicalClassId> ids = FCollection.map(
                this.code.getClassInfo(allClassNames), ClassInfo.toFullClassId());
        this.history().recordClassPath(ids, coverageData);
    }

    /**
     *
     * @param units
     * @return
     * @throws IOException
     */
    public Collection<MutationResult> runMutants(List<MutationTestUnit> units) throws IOException {
        final MutantResultListenerImpl listener = new MutantResultListenerImpl();
        try {
            this.checkMutationsFound(units);
            this.recordClassPath(this.coverageData);
            MutationEngine engine = this.strategies.factory().createEngine(
                    this.data.isMutateStaticInitializers(),
                    Prelude.or(this.data.getExcludedMethods()),
                    this.data.getLoggingClasses(), this.data.getMutators(),
                    this.data.isDetectInlinedCode());
            final List<MutationResultListener> config = Lists.newArrayList(listener);
            final MutationAnalysisExecutor mae = new MutationAnalysisExecutor(this.numberOfThreads(), config);
            this.timings.registerStart(Timings.Stage.RUN_MUTATION_TESTS);
            mae.run(units.stream().map(unit -> (MutationAnalysisUnit) unit).collect(Collectors.toList()));
            this.timings.registerEnd(Timings.Stage.RUN_MUTATION_TESTS);
        } catch (SecurityException | IllegalArgumentException ex) {
            Logger.getLogger(MutationCoverageImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listener.getResults();
    }

    private void verifyBuildSuitableForMutationTesting() {
        this.strategies.buildVerifier().verify(this.code);
    }

}
