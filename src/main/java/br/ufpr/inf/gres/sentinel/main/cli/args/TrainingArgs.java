package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Giovani Guizzo
 */
@Parameters(commandDescription = "If Sentinel must be executed for generating strategies (training).",
			commandNames = "training")
public class TrainingArgs extends MainArgs {

	@Parameter(names = {"--trainingDirectory", "-td"},
			   description = "The directory (relative to the working directory) in which the training programs are located and where the training will be executed.",
			   converter = SeparatorConverter.class)
	public String trainingDirectory = "training";

	@Parameter(names = {"--trainingPrograms", "-tp"},
			   description = "The names of the training programs to generate strategies. Sentinel will search for the programs in /path/to/training/directory/ according to the tool used for the mutant generation.",
			   variableArity = true)
	public List<String> trainingPrograms = Lists.newArrayList("br.ufpr.inf.gres.TriTyp");

	@Parameter(names = "--minLength", description = "Minimum length for the chromosome.")
	public Integer minLength = 15;

	@Parameter(names = "--maxLength", description = "Maximum length for the chromosome.")
	public Integer maxLength = 100;

	@Parameter(names = "--lowerBound", description = "Lower bound for each variable.")
	public Integer lowerVariableBound = 0;

	@Parameter(names = "--upperBound", description = "Upper bound for each variable.")
	public Integer upperVariableBound = 179;

	@Parameter(names = "--maxWraps", description = "Maximum chromosome wraps.")
	public Integer maxWraps = 0;

	@Parameter(names = "--trainingRuns",
			   description = "Number of training runs for each training program in each training evaluation.")
	public Integer numberOfTrainingRuns = 10;

	@Parameter(names = "--maxEvaluations", description = "Maximum number of fitness evaluations.")
	public Integer maxEvaluations = 10000;

	@Parameter(names = "--populationSize", description = "Population size.")
	public Integer populationSize = 100;

	@Parameter(names = "--duplicateProbability", description = "Duplicate probability.")
	public Double duplicateProbability = 0.1D;

	@Parameter(names = "--pruneProbability", description = "Prune probability.")
	public Double pruneProbability = 0.1D;

	@Parameter(names = "--crossoverProbability", description = "Crossover probability.")
	public Double crossoverProbability = 1.0D;

	@Parameter(names = "--mutationProbability", description = "Mutation probability.")
	public Double mutationProbability = 0.01D;

}
