package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation.Rule;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.NonTerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.GroupSelectionOperation;
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
public class GroupSelectionTypeFactoryTest {

	private static Rule testingRule;

	public GroupSelectionTypeFactoryTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		try {
			StrategyMapper strategyMapper = new StrategyMapper(GrammarFiles.getDefaultGrammarPath());
			testingRule = strategyMapper.getNonTerminalRule(NonTerminalRuleType.OPERATOR_GROUP_SELECTION_TYPE);
		} catch (IOException ex) {
			Logger.getLogger(GroupSelectionTypeFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void testCreateOperation() {
		Iterator<Integer> iterator = Lists.newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).iterator();
		Operation operation = FactoryFlyweight.getNonTerminalFactory().createOperation(testingRule, iterator);
		assertNotNull(operation);
		assertTrue(operation instanceof GroupSelectionOperation);
		assertNotNull(((GroupSelectionOperation) operation).getSelectionType());
		assertNotNull(((GroupSelectionOperation) operation).getSorter());
		assertNotNull(((GroupSelectionOperation) operation).getGroupingFunction());
	}

}
