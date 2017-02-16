package br.ufpr.inf.gres.sentinel.main;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import com.beust.jcommander.Parameter;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class CommandLineProperties {

	// Default properties.
	@Parameter(names = {"--help", "-h"}, description = "Shows this message.")
	public boolean help;

	@Parameter(names = {
			"--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"
	},
			   description = "The tool used to effectively generate the mutants. Available options: \"muJava\", \"HG4HOM\" (same as muJava).")
	public String facade = "muJava";

	@Parameter(names = {
			"--grammar", "--grammarFile", "-g"
	},
			   description = "The grammar file used to interpret the strategy(ies). If this is informed, then Sentinel will search for the grammar in: /path/to/working/directory/grammars/.")
	public String grammarFile = "default";

	@Parameter(names = {
			"--grammarFilePath", "--grammarPath", "--path", "-p"
	},
			   description = "The grammar file path (relative to the working directory) used to interpret the strategy(ies). If both -g and -p are informed, -g will be given priority.")
	public String grammarFilePath = GrammarFiles.getDefaultGrammarPath();

	@Parameter(names = {"--workingDirectory", "-w"}, description = "The working directory of Sentinel.")
	public String workingDirectory = System.getProperty("user.dir");

	// Training properties.
	@Parameter(names = {"--training", "-t"},
			   description = "If Sentinel must be executed for generating strategies. Do not trigger it if you want to only execute a given strategy.")
	public boolean training = false;

	@Parameter(names = {"--trainingPrograms", "-tp"},
			   description = "The names of the training programs to generate strategies. If this is informed, then Sentinel will search for the programs in: /path/to/working/directory/training/.",
			   variableArity = true)
	public List<String> trainingPrograms = Lists.newArrayList("TriTyp");

	@Parameter(names = {"--trainingProgramsPaths", "-tpp"},
			   description = "The path of the training programs (relative to the working directory) to generate strategies. If both -tp and -tpp are informed, -tp will be given priority.",
			   variableArity = true)
	public List<String>
			trainingProgramsPaths =
			Lists.newArrayList(workingDirectory + File.separator + "training" + File.separator + "TriTyp");

	// Execution properties.

}
