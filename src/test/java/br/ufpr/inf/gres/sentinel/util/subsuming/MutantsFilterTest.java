package br.ufpr.inf.gres.sentinel.util.subsuming;

import br.ufpr.inf.gres.sentinel.util.subsuming.MutantsFilter;
import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.mutation.TestCase;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class MutantsFilterTest {

    public MutantsFilterTest() {
    }

    @Test
    public void testGetMinimalMutantSet() {
        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", null, null);
        Mutant mutant2 = new Mutant("Mutant2", null, null);
        Mutant mutant3 = new Mutant("Mutant3", null, null);
        Mutant mutant4 = new Mutant("Mutant4", null, null);
        Mutant mutant5 = new Mutant("Mutant5", null, null);
        Mutant mutant6 = new Mutant("Mutant6", null, null);
        List<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4, mutant5, mutant6);

        mutant1.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase4));
        mutant2.getKillingTestCases().addAll(Lists.newArrayList(testCase1));
        mutant3.getKillingTestCases().addAll(Lists.newArrayList(testCase3, testCase4, testCase5));
        mutant4.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase3));
        mutant5.getKillingTestCases().addAll(Lists.newArrayList(testCase2, testCase3, testCase4, testCase5));
        mutant6.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase3, testCase5));

        MutantsFilter filter = new MutantsFilter();
        List<Mutant> minimalMutantSet = filter.getMinimalMutantSet(mutants);
        Assert.assertNotNull(minimalMutantSet);
        Assert.assertEquals(2, minimalMutantSet.size());
        Assert.assertEquals(mutant2, minimalMutantSet.get(0));
        Assert.assertEquals(mutant3, minimalMutantSet.get(1));
    }

    @Test
    public void testGetMinimalMutantSet2() {
        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", null, null);
        Mutant mutant2 = new Mutant("Mutant2", null, null);
        Mutant mutant3 = new Mutant("Mutant3", null, null);
        Mutant mutant4 = new Mutant("Mutant4", null, null);
        Mutant mutant5 = new Mutant("Mutant5", null, null);
        Mutant mutant6 = new Mutant("Mutant6", null, null);
        List<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4, mutant5, mutant6);

        mutant1.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase4));
        mutant2.getKillingTestCases().addAll(Lists.newArrayList(testCase1));
        mutant3.getKillingTestCases().addAll(Lists.newArrayList(testCase3, testCase4, testCase5));
        mutant4.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase3));
        mutant5.getKillingTestCases().addAll(Lists.newArrayList(testCase2, testCase3, testCase4));
        mutant6.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase3, testCase5));

        MutantsFilter filter = new MutantsFilter();
        List<Mutant> minimalMutantSet = filter.getMinimalMutantSet(mutants);
        Assert.assertNotNull(minimalMutantSet);
        Assert.assertEquals(3, minimalMutantSet.size());
        Assert.assertEquals(mutant2, minimalMutantSet.get(0));
        Assert.assertEquals(mutant3, minimalMutantSet.get(1));
        Assert.assertEquals(mutant5, minimalMutantSet.get(2));
    }

    @Test
    public void testGetMinimalMutantSet3() {
        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", null, null);
        Mutant mutant2 = new Mutant("Mutant2", null, null);
        Mutant mutant3 = new Mutant("Mutant3", null, null);
        Mutant mutant4 = new Mutant("Mutant4", null, null);
        Mutant mutant5 = new Mutant("Mutant5", null, null);
        Mutant mutant6 = new Mutant("Mutant6", null, null);
        List<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4, mutant5, mutant6);

        mutant1.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase4));
        mutant2.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2));
        mutant3.getKillingTestCases().addAll(Lists.newArrayList(testCase3, testCase4, testCase5));
        mutant4.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase3));
        mutant5.getKillingTestCases().addAll(Lists.newArrayList(testCase2, testCase3, testCase5));
        mutant6.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase3, testCase5));

        MutantsFilter filter = new MutantsFilter();
        List<Mutant> minimalMutantSet = filter.getMinimalMutantSet(mutants);
        Assert.assertNotNull(minimalMutantSet);
        Assert.assertEquals(4, minimalMutantSet.size());
        Assert.assertEquals(mutant2, minimalMutantSet.get(0));
        Assert.assertEquals(mutant3, minimalMutantSet.get(1));
        Assert.assertEquals(mutant5, minimalMutantSet.get(2));
        Assert.assertEquals(mutant6, minimalMutantSet.get(3));
    }

    @Test
    public void testGetMinimalMutantSet4() {
        TestCase testCase1 = new TestCase("TestCase1");
        TestCase testCase2 = new TestCase("TestCase2");
        TestCase testCase3 = new TestCase("TestCase3");
        TestCase testCase4 = new TestCase("TestCase4");
        TestCase testCase5 = new TestCase("TestCase5");

        Mutant mutant1 = new Mutant("Mutant1", null, null);
        Mutant mutant2 = new Mutant("Mutant2", null, null);
        Mutant mutant3 = new Mutant("Mutant3", null, null);
        Mutant mutant4 = new Mutant("Mutant4", null, null);
        Mutant mutant5 = new Mutant("Mutant5", null, null);
        Mutant mutant6 = new Mutant("Mutant6", null, null);
        List<Mutant> mutants = Lists.newArrayList(mutant1, mutant2, mutant3, mutant4, mutant5, mutant6);

        mutant1.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase4));
        mutant3.getKillingTestCases().addAll(Lists.newArrayList(testCase3, testCase5));
        mutant4.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase2, testCase3));
        mutant5.getKillingTestCases().addAll(Lists.newArrayList(testCase2, testCase3, testCase4, testCase5));
        mutant6.getKillingTestCases().addAll(Lists.newArrayList(testCase1, testCase3, testCase5));

        MutantsFilter filter = new MutantsFilter();
        List<Mutant> minimalMutantSet = filter.getMinimalMutantSet(mutants);
        Assert.assertNotNull(minimalMutantSet);
        Assert.assertEquals(3, minimalMutantSet.size());
        Assert.assertEquals(mutant1, minimalMutantSet.get(0));
        Assert.assertEquals(mutant3, minimalMutantSet.get(1));
        Assert.assertEquals(mutant4, minimalMutantSet.get(2));
    }

}
