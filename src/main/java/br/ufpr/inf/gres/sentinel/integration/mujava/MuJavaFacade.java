package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.hg4hom.core.MutationSystem;
import br.ufpr.inf.gres.hg4hom.core.classpath.ClassInfo;
import br.ufpr.inf.gres.hg4hom.core.enums.MutationOperatorType;
import br.ufpr.inf.gres.hg4hom.core.enums.operator.ClassMutationOperator;
import br.ufpr.inf.gres.hg4hom.core.enums.operator.TraditionalMutationOperator;
import br.ufpr.inf.gres.hg4hom.core.exceptions.OpenJavaException;
import br.ufpr.inf.gres.hg4hom.core.mutation.generator.AbstractMutantsGenerator;
import br.ufpr.inf.gres.hg4hom.core.mutation.generator.SelectionMutantsGeneratorFactory;
import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.beust.jcommander.internal.Lists;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class MuJavaFacade extends IntegrationFacade {

	/**
	 * List of names of class mutation operators
	 */
	public static final ClassMutationOperator[] CM_OPERATORS = ClassMutationOperator.values();
	/**
	 * List of names of traditional mutation operators
	 */
	public static final TraditionalMutationOperator[] TM_OPERATORS = TraditionalMutationOperator.values();
	private String systemHome;

	//	/**
	//	 * List of names of exception-related mutation operators
	//	 */
	//	public static final String[] EM_OPERATORS = {
	//			"EFD", "EHC", "EHD", "EHI", "ETC", "ETD"
	//	};

	public MuJavaFacade() {
	}

	public MuJavaFacade(String systemHome) {
		this.systemHome = systemHome;
	}

	public String getSystemHome() {
		return systemHome;
	}

	public void setSystemHome(String systemHome) {
		this.systemHome = systemHome;
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

	@Override
	public List<Mutant> executeOperator(Operator operator, Program programToBeMutated) {
		MutationSystem.setJMutationStructure(systemHome, programToBeMutated.getSimpleName());

		List<Mutant> mutants = new ArrayList<>();

		MutationOperatorType mutationType;
		if (operator.getType().startsWith("Class")) {
			mutationType = MutationOperatorType.ClassMutation;
		} else {
			mutationType = MutationOperatorType.TraditionalMutation;
		}

		try {
			ClassInfo classInfo = new ClassInfo(programToBeMutated.getSourceFile(), new File(MutationSystem.SRC_PATH));

			// [1] Examine if the target class is testable
			if (classInfo.isTestable()) {
				// [2] Apply mutation testing
				// Remember: The folders are recreated!
				MutationSystem.setMutationSystemPath(classInfo);

				List<String> mutationOperators = Lists.newArrayList(operator.getName());

				AbstractMutantsGenerator mutantsGenerator = new SelectionMutantsGeneratorFactory().getMutantsGeneratorSelector(classInfo, mutationType, mutationOperators);

				mutantsGenerator.makeMutants();
				mutantsGenerator.compileMutants();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (OpenJavaException e) {
			e.printStackTrace();
		}

		return mutants;
	}

	@Override
	public Mutant combineMutants(List<Mutant> mutantsToCombine) {
		//TODO implement it
		return null;
	}

}
