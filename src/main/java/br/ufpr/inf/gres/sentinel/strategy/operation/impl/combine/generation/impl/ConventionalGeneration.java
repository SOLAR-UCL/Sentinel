package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.impl;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.AbstractHOMGeneration;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public class ConventionalGeneration extends AbstractHOMGeneration {

    private int order;
    private SingleHOMGeneration singleHOMGeneration;

    /**
     *
     * @param order
     */
    public ConventionalGeneration(int order) {
        super(TerminalRuleType.CONVENTIONAL + " Generation");
        this.order = order;
        this.singleHOMGeneration = new SingleHOMGeneration();
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<Mutant> doOperation(List<Mutant> input) {
        List<Mutant> result = new ArrayList<>();
        if (input.size() >= 2) {
            List<List<Mutant>> partition = Lists.partition(input, this.order);
            for (List<Mutant> mutantList : partition) {
                if (mutantList.size() >= 2) {
                    result.addAll(this.singleHOMGeneration.doOperation(mutantList));
                }
            }
        }
        return result;
    }

    /**
     *
     * @return
     */
    public int getOrder() {
        return this.order;
    }

    /**
     *
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        return false;
    }
}
