package br.ufpr.inf.gres.sentinel.integration;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Giovani Guizzo
 */
public abstract class IntegrationFacade {

	private static final HashMap<Program, Long> conventionalExecutionTimes = new HashMap<>();
	private static final HashMap<Program, Integer> conventionalQuantities = new HashMap<>();
	private static final HashMap<Program, Double> conventionalScores = new HashMap<>();
	private static final HashMap<Program, List<Mutant>> conventionalMutants = new HashMap<>();
	private static IntegrationFacade FACADE_INSTANCE;
	private static Program PROGRAM_UNDER_TEST;

	public static IntegrationFacade getIntegrationFacade() {
		return FACADE_INSTANCE;
	}

	public static void setIntegrationFacade(IntegrationFacade facade) {
		FACADE_INSTANCE = facade;
	}

	public static Program getProgramUnderTest() {
		return PROGRAM_UNDER_TEST;
	}

	public static void setProgramUnderTest(Program programUnderTest) {
		IntegrationFacade.PROGRAM_UNDER_TEST = programUnderTest;
	}

	public long getConventionalMutationTime(Program program, TimeUnit timeUnit) {
		if (!conventionalExecutionTimes.containsKey(program)) {
			runConventionalStrategy(program);
		}
		return timeUnit.convert(conventionalExecutionTimes.get(program), TimeUnit.NANOSECONDS);
	}

	public int getConventionalQuantityOfMutants(Program program) {
		if (!conventionalExecutionTimes.containsKey(program)) {
			runConventionalStrategy(program);
		}
		return conventionalQuantities.get(program);
	}

	public double getConventionalMutationScore(Program program) {
		if (!conventionalExecutionTimes.containsKey(program)) {
			runConventionalStrategy(program);
		}
		return conventionalScores.get(program);
	}

	public double getRelativeMutationScore(Program program, List<TestCase> testCases) {
		if (!conventionalMutants.containsKey(program)) {
			runConventionalStrategy(program);
		}
		List<Mutant> mutants = new ArrayList<>(conventionalMutants.get(program));
		double relativeScore = mutants.size();
		mutants.retainAll(testCases.stream()
								   .map(TestCase::getKillingMutants)
								   .reduce((mutants1, mutants2) -> SetUniqueList.setUniqueList(Lists.newArrayList(
										   Iterables.concat(mutants1, mutants2))))
								   .get());
		return (double) mutants.size() / relativeScore;
	}

	protected void runConventionalStrategy(Program program) {
		Program tempProgram = IntegrationFacade.getProgramUnderTest();
		IntegrationFacade.setProgramUnderTest(program);
		Stopwatch stopwatch = Stopwatch.createStarted();
		List<Operator> operators = getAllOperators();
		List<Mutant> allMutants = new ArrayList<>();
		for (Operator operator : operators) {
			allMutants.addAll(executeOperator(operator));
		}
		executeMutants(allMutants);
		stopwatch.stop();
		conventionalExecutionTimes.put(program, stopwatch.elapsed(TimeUnit.NANOSECONDS));
		conventionalQuantities.put(program, allMutants.size());
		long numberOfDeadMutants = allMutants.stream().filter(mutant -> !mutant.isAlive()).count();
		conventionalScores.put(program, (double) numberOfDeadMutants / (double) allMutants.size());
		conventionalMutants.put(program, allMutants);
		IntegrationFacade.setProgramUnderTest(tempProgram);
	}

	public abstract List<Operator> getAllOperators();

	public abstract List<Mutant> executeOperator(Operator operator);

	public abstract Mutant combineMutants(List<Mutant> mutantsToCombine);

	public abstract void executeMutant(Mutant mutantToExecute);

	public abstract void executeMutants(List<Mutant> mutantsToExecute);

}
