package br.ufpr.inf.gres.sentinel.base.mutation;

import br.ufpr.inf.gres.sentinel.integration.IntegrationFacade;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class TestCaseTest {

	public TestCaseTest() {
	}

	@Test
	public void testHashCode() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = new TestCase("Test1");
		assertEquals(testCase.hashCode(), testCase2.hashCode());
	}

	@Test
	public void testHashCode2() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = testCase;
		assertEquals(testCase.hashCode(), testCase2.hashCode());
	}

	@Test
	public void testHashCode3() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = new TestCase("Test2");
		assertNotEquals(testCase.hashCode(), testCase2.hashCode());
	}

	@Test
	public void testEquals() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = new TestCase("Test1");
		assertEquals(testCase, testCase2);
	}

	@Test
	public void testEquals2() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = testCase;
		assertEquals(testCase, testCase2);
	}

	@Test
	public void testEquals3() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = new TestCase("Test2");
		assertNotEquals(testCase, testCase2);
	}

	@Test
	public void testEquals4() {
		TestCase testCase = new TestCase("Test1");
		TestCase testCase2 = null;
		assertNotEquals(testCase, testCase2);
	}

	@Test
	public void testEquals5() {
		TestCase testCase = new TestCase("Test1");
		Object testCase2 = new Object();
		assertNotEquals(testCase, testCase2);
	}

	@Test
	public void testCloneConstructor() {
		TestCase instance = new TestCase("Test1");
		instance.getKillingMutants().add(new Mutant("Mutant1", null, IntegrationFacade.getProgramUnderTest()));
		instance.getKillingMutants().add(new Mutant("Mutant2", null, IntegrationFacade.getProgramUnderTest()));
		TestCase instance2 = new TestCase(instance);
		assertEquals(instance, instance2);
		assertArrayEquals(instance.getKillingMutants().toArray(), instance2.getKillingMutants().toArray());
	}

	@Test
	public void testToString() {
		TestCase testCase = new TestCase("Test1");
		assertEquals("Test1", testCase.toString());
	}

	@Test
	public void testGetAndSetName() {
		TestCase testCase = new TestCase("Test1");
		testCase.setName("TestTest");
		String result = testCase.getName();
		assertEquals("TestTest", result);
	}

	@Test
	public void testGetAndSetKillingMutants() {
		TestCase testCase = new TestCase("Test1");
		SetUniqueList<Mutant> setUniqueList = SetUniqueList.setUniqueList(new ArrayList<>());
		testCase.setKillingMutants(setUniqueList);
		SetUniqueList result = testCase.getKillingMutants();
		assertEquals(setUniqueList, result);
	}

}
