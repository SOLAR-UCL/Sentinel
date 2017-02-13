package br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.impl;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.problem.AbstractVariableLengthIntegerProblem;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.impl.DefaultVariableLengthIntegerSolution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.StrategyMapper;

import java.io.IOException;

/**
 * @author Giovani Guizzo
 */
public class MutationStrategyGenerationProblem implements AbstractVariableLengthIntegerProblem {

	private final StrategyMapper strategyMapper;
	private int lowerVariableBound;
	private int upperVariableBound;
	private int minLength;
	private int maxLength;
	private int maxWraps;

	public MutationStrategyGenerationProblem(String grammarFile,
											 int minLength,
											 int maxLength,
											 int lowerVariableBound,
											 int upperVariableBound,
											 int maxWraps) throws IOException {
		this.strategyMapper = new StrategyMapper(grammarFile);
		this.lowerVariableBound = lowerVariableBound;
		this.upperVariableBound = upperVariableBound;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.maxWraps = maxWraps;
	}

	@Override
	public int getUpperVariableBound() {
		return upperVariableBound;
	}

	@Override
	public int getLowerVariableBound() {
		return lowerVariableBound;
	}

	@Override
	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public int getMinLength() {
		return minLength;
	}

	@Override
	public int getNumberOfVariables() {
		return minLength;
	}

	@Override
	public int getNumberOfObjectives() {
		return 3;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String getName() {
		return "Mutation Strategy Generation Problem";
	}

	@Override
	public void evaluate(VariableLengthSolution<Integer> variableLengthIntegerSolution) {
		//TODO implement it
	}

	@Override
	public VariableLengthSolution<Integer> createSolution() {
		return new DefaultVariableLengthIntegerSolution(this);
	}
}
