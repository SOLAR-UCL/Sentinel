package br.ufpr.inf.gres.sentinel.integration.mujava;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.Operator;
import br.ufpr.inf.gres.sentinel.base.mutation.Program;
import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class HG4HOMFacadeTest {

	public HG4HOMFacadeTest() {
	}

	@Test
	public void getAllOperators() throws Exception {
		IntegrationFacade muJavaFacade = new HG4HOMFacade("");
		List<Operator> allOperators = muJavaFacade.getAllOperators();
		assertNotNull(allOperators);
		assertEquals(47, allOperators.size());
	}

	@Test
	public void executeOperator() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("LOI", "Traditional_L");

		List<Mutant> mutants = facade.executeOperator(operator);
		assertEquals(57, mutants.size());
		for (Mutant mutant : mutants) {
			assertTrue(mutant.getOperators().contains(operator));
		}
		assertFalse(mutants.get(0).getKillingTestCases().isEmpty());
	}

	@Test
	public void executeOperator2() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);
		assertEquals(39, mutants.size());
		for (Mutant mutant : mutants) {
			assertTrue(mutant.getOperators().contains(operator));
		}
		assertFalse(mutants.get(16).getKillingTestCases().isEmpty());
	}

	@Test(expected = Exception.class)
	public void executeOperator3() throws Exception {
		Program programUnderTest = new Program("test.Unknown", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);
	}

	@Test(expected = Exception.class)
	public void executeOperator4() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("wrongPath"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);
	}

	@Test(expected = Exception.class)
	public void executeOperator5() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "wrongPath");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);
	}

	@Test
	@Ignore
	public void executeOperator6() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Stopwatch stopwatch = Stopwatch.createStarted();
		List<Mutant> aliveMutants = new ArrayList<>();
		List<Mutant> deadMutants = new ArrayList<>();
		for (Operator operator : facade.getAllOperators()) {
			List<Mutant> mutants = facade.executeOperator(operator);
			aliveMutants.addAll(mutants.stream().filter(mutant -> mutant.isAlive()).collect(Collectors.toList()));
			deadMutants.addAll(mutants.stream().filter(mutant -> !mutant.isAlive()).collect(Collectors.toList()));
		}
		stopwatch.stop();

		System.out.println("Alive: " + aliveMutants.size());
		System.out.println("Dead: " + deadMutants.size());
		System.out.println("Time s: " + stopwatch.elapsed(TimeUnit.SECONDS) + "s");
		System.out.println("Time m: " + stopwatch.elapsed(TimeUnit.MINUTES) + "m");
		assertEquals(79, aliveMutants.size());
		assertEquals(526, deadMutants.size());
	}

	@Test
	public void combineMutants() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);

		mutants = Lists.newArrayList(mutants.get(0), mutants.get(3));
		Mutant hom = facade.combineMutants(mutants);

		assertNotNull(hom);
		assertEquals("AMC_1_and_AMC_4", hom.getFullName());
		assertEquals(1, hom.getOperators().size());
		assertEquals(2, hom.getOrder());
		assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
		assertTrue(hom.isAlive());
		assertEquals(programUnderTest, hom.getOriginalProgram());
	}

	@Test(expected = Exception.class)
	public void combineMutants2() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);
		mutants = Lists.newArrayList(mutants.get(0), mutants.get(1));

		Mutant hom = facade.combineMutants(mutants);
		assertNull(hom);

		hom = facade.combineMutants(Lists.newArrayList(hom, mutants.get(0)));
		assertNull(hom);

		facade.setMuJavaHome("Unknown.invalid");
		facade.combineMutants(mutants);
	}

	@Test
	public void combineMutants3() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");

		List<Mutant> mutants = facade.executeOperator(operator);

		mutants = Lists.newArrayList(mutants.get(0), mutants.get(16));
		Mutant hom = facade.combineMutants(mutants);

		assertNotNull(hom);
		assertEquals("AMC_1_and_AMC_17", hom.getFullName());
		assertEquals(1, hom.getOperators().size());
		assertEquals(2, hom.getOrder());
		assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
		assertFalse(hom.isAlive());
		assertEquals(programUnderTest, hom.getOriginalProgram());
	}

	@Test
	public void combineMutants4() throws Exception {
		Program programUnderTest = new Program("br.ufpr.inf.gres.TriTyp", new File("src/test/resources/testfiles/TriTyp/src/br/ufpr/inf/gres/TriTyp.java"));

		HG4HOMFacade facade = new HG4HOMFacade(System.getProperty("user.dir") + File.separator + "src/test/resources/testfiles");

		IntegrationFacade.setIntegrationFacade(facade);
		IntegrationFacade.setProgramUnderTest(programUnderTest);

		Operator operator = new Operator("AMC", "Class_A");
		List<Mutant> mutants = facade.executeOperator(operator);

		operator = new Operator("LOI", "Traditional_L");
		mutants.addAll(facade.executeOperator(operator));

		mutants = Lists.newArrayList(mutants.get(0), mutants.get(39));
		Mutant hom = facade.combineMutants(mutants);

		assertNotNull(hom);
		assertEquals("AMC_1_and_LOI_1", hom.getFullName());
		assertEquals(2, hom.getOperators().size());
		assertEquals(2, hom.getOrder());
		assertArrayEquals(mutants.toArray(), hom.getConstituentMutants().toArray());
		assertFalse(hom.isAlive());
		assertEquals(programUnderTest, hom.getOriginalProgram());
	}

	@Test
	public void getAndSetMuJavaHome() throws Exception {
		HG4HOMFacade facade = new HG4HOMFacade("Test");
		facade.setMuJavaHome("Test2");
		assertEquals("Test2", facade.getMuJavaHome());
	}

}
