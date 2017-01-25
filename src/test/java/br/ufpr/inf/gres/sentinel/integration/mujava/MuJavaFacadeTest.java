package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class MuJavaFacadeTest {

	public MuJavaFacadeTest() {
	}

	@Test
	public void getAllOperators() throws Exception {
		IntegrationFacade muJavaFacade = new MuJavaFacade();
		List<Operator> allOperators = muJavaFacade.getAllOperators();
		assertNotNull(allOperators);
		assertEquals(47, allOperators.size());
	}

	@Test
	public void executeOperator() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		MuJavaFacade facade = new MuJavaFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("LOI", "Traditional_L");

		List<Mutant> mutants = facade.executeOperator(operator, programUnderTest);
		assertEquals(57, mutants.size());
		for (Mutant mutant : mutants) {
			assertTrue(mutant.getOperators().contains(operator));
		}
		assertFalse(mutants.get(0).getKillingTestCases().isEmpty());
	}

	@Test
	public void executeOperator2() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		MuJavaFacade facade = new MuJavaFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator, programUnderTest);
		assertEquals(39, mutants.size());
		for (Mutant mutant : mutants) {
			assertTrue(mutant.getOperators().contains(operator));
		}
		assertFalse(mutants.get(10).getKillingTestCases().isEmpty());
	}

	@Test
	public void combineMutants() throws Exception {

	}

}
