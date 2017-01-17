package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class MuJavaFacade extends IntegrationFacade {

	/**
	 * List of names of class mutation operators
	 */
	public static final String[] CM_OPERATORS = {
			"IHI",
			"IHD",
			"IOD",
			"IOP",
			"IOR",
			"ISI",
			"ISD",
			"IPC",
			"PNC",
			"PMD",
			"PPD",
			"PCI",
			"PCC",
			"PCD",
			"PRV",
			"OMR",
			"OMD",
			"OAN",
			"JTI",
			"JTD",
			"JSI",
			"JSD",
			"JID",
			"JDC",
			"EOA",
			"EOC",
			"EAM",
			"EMM"
	};

	/**
	 * List of names of traditional mutation operators
	 */
	public static final String[] TM_OPERATORS = {
			"AORB",
			"AORS",
			"AOIU",
			"AOIS",
			"AODU",
			"AODS",
			"ROR",
			"COR",
			"COD",
			"COI",
			"SOR",
			"LOR",
			"LOI",
			"LOD",
			"ASRS",
			"SDL",
			"VDL",
			"CDL",
			"ODL"
	};

	/**
	 * List of names of exception-related mutation operators
	 */
	public static final String[] EM_OPERATORS = {
			"EFD", "EHC", "EHD", "EHI", "ETC", "ETD"
	};

	@Override
	public List<Operator> getAllOperators() {
		List<Operator> operators = new ArrayList<>();
		for (String operator : CM_OPERATORS) {
			operators.add(new Operator(operator, "Class_" + operator.charAt(0)));
		}
		for (String operator : TM_OPERATORS) {
			operators.add(new Operator(operator, "Traditional_" + operator.charAt(0)));
		}
		for (String operator : EM_OPERATORS) {
			operators.add(new Operator(operator, "Exception_" + operator.charAt(0)));
		}
		return operators;
	}

	@Override
	public List<Mutant> executeOperator(Operator operator, Program programToBeMutated) {
		//TODO implement it
		return Collections.emptyList();
	}

	@Override
	public Mutant combineMutants(List<Mutant> mutantsToCombine) {
		//TODO implement it
		return null;
	}

}
