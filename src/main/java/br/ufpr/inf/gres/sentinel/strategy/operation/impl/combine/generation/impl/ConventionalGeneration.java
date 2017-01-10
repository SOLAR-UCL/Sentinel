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

	private SingleHOMGeneration singleHOMGeneration;
	private int order;

	public ConventionalGeneration(int order) {
		super(TerminalRuleType.CONVENTIONAL + " Generation");
		this.order = order;
		this.singleHOMGeneration = new SingleHOMGeneration();
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public List<Mutant> doOperation(List<Mutant> input) {
		List<Mutant> result = new ArrayList<>();
		List<List<Mutant>> partition = Lists.partition(input, order);
		for (List<Mutant> mutantList : partition) {
			result.addAll(singleHOMGeneration.doOperation(mutantList));
		}
		return result;
	}

	@Override
	public boolean isSpecific() {
		return false;
	}
}
