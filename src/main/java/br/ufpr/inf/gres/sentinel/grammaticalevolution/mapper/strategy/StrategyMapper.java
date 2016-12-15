package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.AbstractGrammarMapper;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.FactoryFlyweight;
import br.ufpr.inf.gres.sentinel.strategy.Strategy;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * The specific Strategy class mapper.
 *
 * @author Giovani Guizzo
 */
@SuppressWarnings("ALL")
public class StrategyMapper extends AbstractGrammarMapper<Strategy> {

	public StrategyMapper() {
		super();
	}

	public StrategyMapper(String grammarFilePath) throws IOException {
		super(grammarFilePath);
	}

	public StrategyMapper(File grammarFile) throws IOException {
		super(grammarFile);
	}

	@Override
	protected Strategy hookInterpret(Iterator<Integer> cyclicIterator) {
		Strategy strategy = new Strategy();
		if (rootNode != null) {
			strategy.setFirstOperation(FactoryFlyweight.getNonTerminalFactory()
													   .createOperation(rootNode, cyclicIterator));
		}
		return strategy;
	}

}
