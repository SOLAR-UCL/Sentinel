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

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MainArgs mainArgs = new MainArgs();
        TrainingArgs trainingArgs = new TrainingArgs();
        TestingArgs testingArgs = new TestingArgs();
        AnalysisArgs analysisArgs = new AnalysisArgs();

        JCommander commander = new JCommander(mainArgs);
        commander.addCommand(trainingArgs);
        commander.addCommand(testingArgs);
        commander.addCommand(analysisArgs);
        commander.setProgramName("Sentinel");

        try {
            commander.parse(args);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            commander.usage();
        }

        if (mainArgs.help) {
            commander.usage();
        } else if (trainingArgs.help || testingArgs.help || analysisArgs.help) {
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
                } else if (chosenCommand instanceof TestingArgs) {
                    SentinelTesting.test(testingArgs, args);
                } else if (chosenCommand instanceof AnalysisArgs) {
                    SentinelAnalysis.analyse(analysisArgs, args);
                }
            }
        }
    }

}
