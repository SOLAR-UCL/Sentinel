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
public class StrategyMapper extends AbstractGrammarMapper<Strategy> {

    /**
     *
     */
    public StrategyMapper() {
        super();
    }

    /**
     *
     * @param grammarFilePath
     * @throws IOException
     */
    public StrategyMapper(String grammarFilePath) throws IOException {
        super(grammarFilePath);
    }

    /**
     *
     * @param grammarFile
     * @throws IOException
     */
    public StrategyMapper(File grammarFile) throws IOException {
        super(grammarFile);
    }

    @Override
    protected Strategy hookInterpret(Iterator<Integer> integerIterable) {
        Strategy strategy = new Strategy();
        if (this.rootNode != null) {
            strategy.setFirstOperation(FactoryFlyweight.getNonTerminalFactory()
                    .createOperation(this.rootNode, integerIterable));
        }
        return strategy;
    }

}
