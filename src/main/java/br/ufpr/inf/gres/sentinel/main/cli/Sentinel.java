package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
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

    public static void main(String[] args) throws Exception {
        args = new String[]{"train", "--maxEvaluations", "1", "--populationSize", "1", "--trainingRuns", "1"};
//        args = new String[]{"-h"};

        MainArgs mainArgs = new MainArgs();
        TrainingArgs trainingArgs = new TrainingArgs();
        TestingArgs testingArgs = new TestingArgs();
        AnalysisArgs analysisArgs = new AnalysisArgs();

        JCommander commander = new JCommander(mainArgs);
        commander.addCommand(trainingArgs);
        commander.addCommand(testingArgs);
        commander.addCommand(analysisArgs);
        commander.setProgramName("Sentinel");
        commander.setAllowParameterOverwriting(true);

        try {
            commander.parse(args);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            usage(commander);
        }

        if (mainArgs.help) {
            usage(commander);
        } else if (trainingArgs.help || testingArgs.help || analysisArgs.help) {
            commander.usage(commander.getParsedCommand());
            System.exit(0);
        }

        JCommander command = commander.getCommands().get(commander.getParsedCommand());
        if (command == null) {
            System.out.println("Command not found. Here are the usage instructions for you.");
            usage(commander);
        }

        Object chosenCommand = command.getObjects().get(0);
        if (chosenCommand instanceof TrainingArgs) {
            SentinelTraining.train(trainingArgs, args);
        } else if (chosenCommand instanceof TestingArgs) {
            SentinelTesting.test(testingArgs, args);
        } else if (chosenCommand instanceof AnalysisArgs) {
            SentinelAnalysis.analyse(analysisArgs, args);
        }
    }

    private static void usage(JCommander commander) {
        commander.usage();
        System.exit(0);
    }

}
