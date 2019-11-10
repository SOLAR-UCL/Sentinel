package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.BenchmarkArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.CacheArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.MainArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.TestingArgs;
import br.ufpr.inf.gres.sentinel.main.cli.args.TrainingArgs;
import com.beust.jcommander.JCommander;

/**
 * Main class.
 *
 * @author Giovani Guizzo
 */
public class Sentinel {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MainArgs mainArgs = new MainArgs();
        TrainingArgs trainingArgs = new TrainingArgs();
        CacheArgs cacheArgs = new CacheArgs();
        TestingArgs testingArgs = new TestingArgs();
        AnalysisArgs analysisArgs = new AnalysisArgs();
        BenchmarkArgs benchmarkArgs = new BenchmarkArgs();

        JCommander commander = new JCommander(mainArgs);
        commander.addCommand(trainingArgs);
        commander.addCommand(testingArgs);
        commander.addCommand(analysisArgs);
        commander.addCommand(cacheArgs);
        commander.addCommand(benchmarkArgs);
        commander.setProgramName("Sentinel");

        try {
            commander.parse(args);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            commander.usage();
            return;
        }

        if (mainArgs.help) {
            commander.usage();
        } else if (trainingArgs.help || testingArgs.help || analysisArgs.help || cacheArgs.help || benchmarkArgs.help) {
            commander.usage(commander.getParsedCommand());
        } else {

            JCommander command = commander.getCommands().get(commander.getParsedCommand());
            if (command == null) {
                System.out.println("Command not found. Here are the usage instructions for you.");
                commander.usage();
            } else {
                Object chosenCommand = command.getObjects().get(0);
                if (chosenCommand instanceof TrainingArgs) {
                    SentinelTraining.train(trainingArgs, args);
                } else if (chosenCommand instanceof CacheArgs) {
                    SentinelCaching.cache(cacheArgs, args);
                } else if (chosenCommand instanceof TestingArgs) {
                    SentinelTesting.test(testingArgs, args);
                } else if (chosenCommand instanceof AnalysisArgs) {
                    SentinelAnalysis.analyse(analysisArgs, args);
                }  else if (chosenCommand instanceof BenchmarkArgs) {
                    SentinelBenchmarking.benchmark(benchmarkArgs, args);
                }
            }
        }
    }

}
