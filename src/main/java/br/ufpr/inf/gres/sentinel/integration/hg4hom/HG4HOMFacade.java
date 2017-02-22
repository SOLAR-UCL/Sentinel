package br.ufpr.inf.gres.sentinel.integration.hg4hom;

import br.ufpr.inf.gres.hg4hom.core.MutationSystem;
import br.ufpr.inf.gres.hg4hom.core.classpath.ClassInfo;
import br.ufpr.inf.gres.hg4hom.core.classpath.Resources;
import br.ufpr.inf.gres.hg4hom.core.enums.MutationOperatorType;
import br.ufpr.inf.gres.hg4hom.core.enums.MutationTestResultType;
import br.ufpr.inf.gres.hg4hom.core.enums.operator.ClassMutationOperator;
import br.ufpr.inf.gres.hg4hom.core.enums.operator.TraditionalMutationOperator;
import br.ufpr.inf.gres.hg4hom.core.exceptions.HomException;
import br.ufpr.inf.gres.hg4hom.core.exceptions.NoMutantDirException;
import br.ufpr.inf.gres.hg4hom.core.mutation.generator.AbstractMutantsGenerator;
import br.ufpr.inf.gres.hg4hom.core.mutation.generator.SelectionMutantsGeneratorFactory;
import br.ufpr.inf.gres.hg4hom.core.mutation.type.Fom;
import br.ufpr.inf.gres.hg4hom.core.mutation.type.Hom;
import br.ufpr.inf.gres.hg4hom.core.test.execution.TestResult;
import br.ufpr.inf.gres.hg4hom.core.test.runner.ClassMutantsBuilder;
import br.ufpr.inf.gres.hg4hom.core.test.runner.TraditionalMutantsBuilder;
import br.ufpr.inf.gres.hg4hom.core.util.mutation.MutationLog;
import br.ufpr.inf.gres.hg4hom.hom.runner.HomBuilder;
import br.ufpr.inf.gres.hg4hom.hom.strategies.impl.EachChoiceStrategy;
import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.list.SetUniqueList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Giovani Guizzo
 */
public class HG4HOMFacade extends IntegrationFacade {

	/**
	 * List of names of class mutation operators
	 */
	public static final ClassMutationOperator[] CM_OPERATORS = ClassMutationOperator.values();

	/**
	 * List of names of traditional mutation operators
	 */
	public static final TraditionalMutationOperator[] TM_OPERATORS = TraditionalMutationOperator.values();

	private String muJavaHome;

	public HG4HOMFacade(String muJavaHome) {
		this.muJavaHome = muJavaHome;
		MutationSystem.setJMutationStructureWithoutSession(muJavaHome);
	}

	public String getMuJavaHome() {
		return muJavaHome;
	}

	public void setMuJavaHome(String muJavaHome) {
		this.muJavaHome = muJavaHome;
		MutationSystem.setJMutationStructureWithoutSession(muJavaHome);
	}

	@Override
	public List<Operator> getAllOperators() {
		List<Operator> operators = new ArrayList<>();
		for (ClassMutationOperator operator : CM_OPERATORS) {
			operators.add(new Operator(operator.getValue(), "Class_" + operator.getValue().charAt(0)));
		}
		for (TraditionalMutationOperator operator : TM_OPERATORS) {
			operators.add(new Operator(operator.getValue(), "Traditional_" + operator.getValue().charAt(0)));
		}
		return operators;
	}

	public List<Mutant> executeOperator(Operator operator) {
		Program programToBeMutated = IntegrationFacade.getProgramUnderTest();

		Preconditions.checkNotNull(operator, "Operator cannot be null.");
		Preconditions.checkNotNull(programToBeMutated, "Program to be Mutated cannot be null.");

		List<Mutant> mutants = new ArrayList<>();

		MutationOperatorType mutationType;
		if (operator.getType().startsWith("Class")) {
			mutationType = MutationOperatorType.ClassMutation;
		} else {
			mutationType = MutationOperatorType.TraditionalMutation;
		}

		try {
			ClassInfo
					originalClass =
					new ClassInfo(programToBeMutated.getSourceFile(), new File(MutationSystem.SRC_PATH));

			Preconditions.checkArgument(programToBeMutated.getFullName().equals(originalClass.getWholeClassName()),
										"Program name do not match its source file.");

			MutationSystem.setMutationSystemPath(originalClass);
			MutationSystem.recordInheritanceRelation();

			List<String> mutationOperators = Lists.newArrayList(operator.getName());

			AbstractMutantsGenerator
					mutantsGenerator =
					new SelectionMutantsGeneratorFactory().getMutantsGeneratorSelector(originalClass,
																					   mutationType,
																					   mutationOperators);
			mutantsGenerator.makeMutants();
			mutantsGenerator.compileMutants();

			MutationLog<Fom> mutationLog = new MutationLog<>();
			ArrayList<Fom> loadedFoms = new ArrayList<>();
			if (mutationType.equals(MutationOperatorType.TraditionalMutation)) {
				MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH;
			} else {
				MutationSystem.MUTANT_PATH = MutationSystem.CLASS_MUTANT_PATH;
			}
			mutationLog.setPath(MutationSystem.MUTANT_PATH);
			loadedFoms.addAll(mutationLog.load(Fom[].class));
			loadedFoms.removeIf(fom -> !fom.getOperator().equals(operator.getName()));
			for (Fom fom : loadedFoms) {
				String mutantName = fom.getName();
				Mutant mutant = new Mutant(mutantName, new File(fom.getPath()), programToBeMutated);
				mutant.getOperators().add(operator);
				mutants.add(mutant);
				operator.getGeneratedMutants().add(mutant);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		mutants.sort(Comparator.comparing(Program::getFullName));
		return mutants;
	}

	@Override
	public Mutant combineMutants(List<Mutant> mutantsToCombine) {
		Preconditions.checkNotNull(mutantsToCombine, "Mutant List cannot be null.");
		Preconditions.checkArgument(mutantsToCombine.size() >= 2,
									"There must be at least 2 mutants in the list. Unfortunately, more than 2 mutants is not supported right now and only one HOM will be generated. Found mutants: " +
									mutantsToCombine.size() +
									".");
		mutantsToCombine = mutantsToCombine.subList(0, 2);
		try {
			if (mutantsToCombine.stream().noneMatch(Mutant::isHigherOrder)) {
				Program programToBeMutated = IntegrationFacade.getProgramUnderTest();

				ClassInfo
						originalClass =
						new ClassInfo(programToBeMutated.getSourceFile(), new File(MutationSystem.SRC_PATH));
				ClassInfo
						testSet =
						new Resources(MutationSystem.TESTSET_PATH).getClasses()
																  .stream()
																  .filter(test -> test.getClassName()
																					  .equals(programToBeMutated.getSimpleName() +
																							  "Test"))
																  .collect(Collectors.toList())
																  .get(0);

				MutationSystem.setMutationSystemPath(originalClass);

				MutationLog<Fom> mutationLog = new MutationLog<>();
				mutationLog.setPath(MutationSystem.CLASS_MUTANT_PATH);
				ArrayList<Fom> foms = mutationLog.load(Fom[].class);
				mutationLog.setPath(MutationSystem.TRADITIONAL_MUTANT_PATH);
				foms.addAll(mutationLog.load(Fom[].class));
				List<String>
						mutantNames =
						mutantsToCombine.stream().map(Program::getFullName).collect(Collectors.toList());
				foms.removeIf(fom -> !mutantNames.contains(fom.getName()));

				MutationSystem.MUTANT_PATH = MutationSystem.HIGHER_ORDER_MUTANT_PATH;
				HomBuilder builder = new HomBuilder(new EachChoiceStrategy(foms), originalClass, testSet);
				Hom hom = builder.create();
				builder.compile(hom);

				Mutant generatedHom = new Mutant(hom.getName(), new File(hom.getPath()), programToBeMutated);
				generatedHom.getConstituentMutants().addAll(mutantsToCombine);
				generatedHom.getOperators()
							.addAll(mutantsToCombine.stream()
													.map(Mutant::getOperators)
													.reduce((operators, operators2) -> {
														SetUniqueList<Operator>
																union =
																SetUniqueList.setUniqueList(new ArrayList<>());
														union.addAll(operators);
														union.addAll(operators2);
														return union;
													}).orElse(SetUniqueList.setUniqueList(new ArrayList<>())));
				generatedHom.getOperators().forEach(operator -> operator.getGeneratedMutants().add(generatedHom));
				return generatedHom;
			}
		} catch (HomException ignored) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public void executeMutant(Mutant mutantToExecute) {
		executeMutants(Lists.newArrayList(mutantToExecute));
	}

	@Override
	public void executeMutants(List<Mutant> mutantsToExecute) {
		Program programToBeMutated = IntegrationFacade.getProgramUnderTest();
		Preconditions.checkNotNull(programToBeMutated, "Program to be Mutated cannot be null.");

		try {
			ClassInfo
					originalClass =
					new ClassInfo(programToBeMutated.getSourceFile(), new File(MutationSystem.SRC_PATH));
			ClassInfo
					testSet =
					new Resources(MutationSystem.TESTSET_PATH).getClasses()
															  .stream()
															  .filter(test -> test.getClassName()
																				  .equals(programToBeMutated.getSimpleName() +
																						  "Test"))
															  .collect(Collectors.toList())
															  .get(0);

			MutationSystem.setMutationSystemPath(originalClass);

			Map<Boolean, List<Mutant>>
					mutants =
					mutantsToExecute.stream().collect(Collectors.groupingBy(Mutant::isHigherOrder));

			// Executing HOMs
			List<Mutant> homs = mutants.get(true);
			if (homs != null && !homs.isEmpty()) {
				List<String> homsNames = homs.stream().map(Program::getFullName).collect(Collectors.toList());

				MutationSystem.MUTANT_PATH = MutationSystem.HIGHER_ORDER_MUTANT_PATH;
				HomBuilder builder = new HomBuilder(new EachChoiceStrategy(new ArrayList<>()), originalClass, testSet);
				ArrayList<Hom> allHoms = builder.loadHoms("mutation_log.json");
				allHoms.removeIf(hom -> !homsNames.contains(hom.getName()));
				for (Hom hom : allHoms) {
					builder.test(hom);
					Mutant
							foundHom =
							homs.stream()
								.filter(mutant -> mutant.getFullName().equals(hom.getName()))
								.findFirst()
								.get();
					foundHom.getKillingTestCases()
							.addAll(hom.getTestCases().stream().map(TestCase::new).collect(Collectors.toList()));
					for (TestCase testCase : foundHom.getKillingTestCases()) {
						testCase.getKillingMutants().add(foundHom);
					}
				}
			}

			// Executing FOMs
			List<Mutant> foms = mutants.get(false);
			if (foms != null && !foms.isEmpty()) {
				mutants =
						foms.stream()
							.collect(Collectors.groupingBy(mutant -> mutant.getOperators()
																		   .get(0)
																		   .getType()
																		   .startsWith("Class")));

				// Executing Traditional Mutants
				List<Mutant> traditionalMutants = mutants.get(false);
				if (traditionalMutants != null && !traditionalMutants.isEmpty()) {
					List<String>
							traditionalFomsNames =
							traditionalMutants.stream().map(Program::getFullName).collect(Collectors.toList());
					TraditionalMutantsBuilder
							traditionalMutantsBuilder =
							new TraditionalMutantsBuilder(testSet, originalClass);
					List<String> methods = traditionalMutantsBuilder.getMethods();
					for (String method : methods) {
						MutationSystem.MUTANT_PATH = MutationSystem.TRADITIONAL_MUTANT_PATH + File.separator + method;
						traditionalMutantsBuilder.runMutants(traditionalMutantsBuilder.getMutants()
																					  .stream()
																					  .filter(traditionalFomsNames::contains)
																					  .collect(Collectors.toList()));
					}
					computeTestResults(traditionalMutants, traditionalMutantsBuilder.classifyResult());
				}

				// Executing Class Mutants
				List<Mutant> classMutants = mutants.get(true);
				if (classMutants != null && !classMutants.isEmpty()) {
					List<String>
							classFomsNames =
							classMutants.stream().map(Program::getFullName).collect(Collectors.toList());
					ClassMutantsBuilder classMutantsBuilder = new ClassMutantsBuilder(testSet, originalClass);
					MutationSystem.MUTANT_PATH = MutationSystem.CLASS_MUTANT_PATH;
					classMutantsBuilder.runMutants(classMutantsBuilder.getMutants()
																	  .stream()
																	  .filter(classFomsNames::contains)
																	  .collect(Collectors.toList()));

					computeTestResults(classMutants, classMutantsBuilder.classifyResult());
				}
			}
		} catch (HomException ignored) {
		} catch (IOException | NoMutantDirException e) {
			throw new RuntimeException(e);
		}
	}

	private void computeTestResults(List<Mutant> mutants, TestResult testResult) {
		for (Map.Entry<String, MutationTestResultType> entry : testResult.mutantState.entrySet()) {
			String mutantName = entry.getKey();
			Mutant
					foundMutant =
					mutants.stream().filter(mutant -> mutant.getFullName().equals(mutantName)).findFirst().get();
			ArrayList<String> testCases = testResult.testCaseMutants.get(mutantName);
			foundMutant.getKillingTestCases()
					   .addAll(testCases.stream().map(TestCase::new).collect(Collectors.toList()));
			for (TestCase testCase : foundMutant.getKillingTestCases()) {
				testCase.getKillingMutants().add(foundMutant);
			}
		}
	}

	@Override
	public List<Program> instantiatePrograms(List<String> programNames) {
		List<Program> programs = new ArrayList<>();
		for (String programName : programNames) {
			programs.add(instantiateProgram(programName));
		}
		return programs;
	}

	@Override
	public Program instantiateProgram(String programName) {
		String replace = programName.replace(".java", "");
		replace = CharMatcher.anyOf("\\/.").replaceFrom(replace, File.separator);
		return new Program(programName, new File(MutationSystem.SRC_PATH + File.separator + replace + ".java"));
	}
}
