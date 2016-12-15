package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Option;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.Factory;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.AbstractSorterOperation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.MutantQuantityInGroupComparator;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.sort.impl.operator.OperatorQuantityInGroupComparator;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * @author Giovani Guizzo
 */
public class OperatorGroupSortingFactoryTest {

	private static Rule testingRule;

	public OperatorGroupSortingFactoryTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		try {
			StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
			testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_GROUP_SORTING);
		} catch (IOException ex) {
			Logger.getLogger(OperatorGroupSortingFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void testCreateOperation() {
		Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
		Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
		assertNotNull(operation);
		assertTrue(operation instanceof MutantQuantityInGroupComparator);
	}

	@Test
	public void testCreateOperation2() {
		Iterator<Integer> iterator = Lists.newArrayList(0, 1, 0).iterator();
		Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
		assertNotNull(operation);
		assertTrue(operation instanceof OperatorQuantityInGroupComparator);
	}

	@Test
	public void testCreateOperation3() {
		Iterator<Integer> iterator = Lists.newArrayList(0, 0, 1).iterator();
		Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
		assertNotNull(operation);
		assertTrue(operation instanceof AbstractSorterOperation);
		assertFalse(operation instanceof OperatorQuantityInGroupComparator);
		assertFalse(operation instanceof MutantQuantityInGroupComparator);
	}

	@Test
	public void testCreateOperation4() {
		Iterator<Integer> iterator = Lists.newArrayList(1).iterator();
		Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
		assertNull(operation);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidOperation() {
		Factory factory = OperatorGroupSortingFactory.getInstance();
		Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0).iterator();
		factory.createOperation(new Option(Lists.newArrayList(new Rule("Unknown", Lists.newArrayList(new Option(Lists.newArrayList(new Rule("Unknown"))))))), iterator);
	}

}
