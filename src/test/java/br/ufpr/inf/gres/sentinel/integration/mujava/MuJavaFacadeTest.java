package br.ufpr.inf.gres.sentinel.integration.mujava;

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

		MuJavaFacade facade = new MuJavaFacade("src/test/resources/testfiles/");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		facade.executeOperator(new Operator("ROR", "Traditional_L"), programUnderTest);
	}

	@Test
	public void combineMutants() throws Exception {

	}

}
