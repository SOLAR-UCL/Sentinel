package br.ufpr.inf.gres.sentinel.integration.pit;

import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.pitest.classpath.ClassPath;
import org.pitest.classpath.ClassPathByteArraySource;
import org.pitest.classpath.CodeSource;
import org.pitest.classpath.ProjectClassPaths;
import org.pitest.coverage.CoverageDatabase;
import org.pitest.coverage.CoverageGenerator;
import org.pitest.coverage.execute.CoverageOptions;
import org.pitest.coverage.execute.DefaultCoverageGenerator;
import org.pitest.functional.Option;
import org.pitest.mutationtest.HistoryStore;
import org.pitest.mutationtest.MutationResult;
import org.pitest.mutationtest.MutationResultListenerFactory;
import org.pitest.mutationtest.build.MutationAnalysisUnit;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.config.SettingsFactory;
import org.pitest.mutationtest.incremental.WriterFactory;
import org.pitest.mutationtest.incremental.XStreamHistoryStore;
import org.pitest.mutationtest.tooling.JarCreatingJarFinder;
import org.pitest.mutationtest.tooling.KnownLocationJavaAgentFinder;
import org.pitest.mutationtest.tooling.MutationStrategies;
import org.pitest.process.JavaAgent;
import org.pitest.process.LaunchOptions;
import org.pitest.util.ResultOutputStrategy;
import org.pitest.util.Timings;

/**
 *
 * @author Giovani Guizzo
 */
public class EntryPointImpl {

    private HashMap<Program, CoverageDatabase> coverageData = new HashMap<>();
    private WriterFactory historyWriter;
    private KnownLocationJavaAgentFinder ja;
    private JavaAgent jac;

    /**
     *
     */
    public void close() {
        if (this.jac != null) {
            this.jac.close();
        }
        if (this.ja != null) {
            this.ja.close();
        }
        if (this.historyWriter != null) {
            this.historyWriter.close();
        }

    }

    private MutationCoverageImpl createMutationCoverageImpl(ReportOptions data, SettingsFactory settings, Map<String, String> environmentVariables, File baseDir) {
        final ClassPath cp = data.getClassPath();
        final Option<Reader> reader = data.createHistoryReader();
        if (this.historyWriter == null) {
            this.historyWriter = data.createHistoryWriter();
        }
        if (this.jac == null) {
            this.jac = new JarCreatingJarFinder(new ClassPathByteArraySource(cp));
        }
        if (this.ja == null) {
            this.ja = new KnownLocationJavaAgentFinder(this.jac.getJarLocation().value());
        }
        final ResultOutputStrategy reportOutput = settings.getOutputStrategy();
        final MutationResultListenerFactory reportFactory = settings
                .createListener();
        final CoverageOptions coverageOptions = settings.createCoverageOptions();
        final LaunchOptions launchOptions = new LaunchOptions(this.ja, settings.getJavaExecutable(), data.getJvmArgs(), environmentVariables);
        final ProjectClassPaths cps = data.getMutationClassPaths();
        final CodeSource code = new CodeSource(cps, coverageOptions.getPitConfig()
                .testClassIdentifier());
        final Timings timings = new Timings();
        final CoverageGenerator coverageDatabase = new DefaultCoverageGenerator(
                baseDir, coverageOptions, launchOptions, code,
                settings.createCoverageExporter(), timings, !data.isVerbose());
        final HistoryStore history = new XStreamHistoryStore(this.historyWriter, reader);
        final MutationStrategies strategies = new MutationStrategies(
                settings.createEngine(), history, coverageDatabase, reportFactory,
                reportOutput);
        CoverageDatabase coverage = this.getCoverageData(coverageDatabase);
        final MutationCoverageImpl report = new MutationCoverageImpl(strategies, baseDir,
                code, data, settings, timings, coverage);
        return report;
    }

    /**
     *
     * @param baseDir
     * @param data
     * @param settings
     * @param environmentVariables
     * @param unitsToExecute
     * @return
     * @throws IOException
     */
    public Collection<MutationResult> executeMutants(File baseDir, ReportOptions data,
            SettingsFactory settings, Map<String, String> environmentVariables,
            MutationAnalysisUnit unitsToExecute) throws IOException {
        MutationCoverageImpl report = this.createMutationCoverageImpl(data, settings, environmentVariables, baseDir);

        return report.runMutants(unitsToExecute);
    }

    /**
     *
     * @param baseDir
     * @param data
     * @param settings
     * @param environmentVariables
     * @return
     * @throws IOException
     */
    public MutationAnalysisUnit generateMutants(File baseDir, ReportOptions data, SettingsFactory settings, Map<String, String> environmentVariables) throws IOException {
        MutationCoverageImpl report = this.createMutationCoverageImpl(data, settings, environmentVariables, baseDir);
        return report.createMutants();
    }

    private CoverageDatabase getCoverageData(final CoverageGenerator coverageDatabase) {
        return this.coverageData.computeIfAbsent(IntegrationFacade.getProgramUnderTest(), (program) -> {
            return coverageDatabase.calculateCoverage();
        });
    }

}
