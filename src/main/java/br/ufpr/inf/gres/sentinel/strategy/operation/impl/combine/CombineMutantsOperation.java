package br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.combine.generation.AbstractHOMGeneration;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Giovani Guizzo
 */
public class CombineMutantsOperation extends Operation<Solution, List<Mutant>> {

	private SelectionOperation<Mutant> selection;
	private AbstractHOMGeneration generation;

	public CombineMutantsOperation() {
		super(TerminalRuleType.COMBINE_MUTANTS);
	}

	public CombineMutantsOperation(AbstractHOMGeneration generation, SelectionOperation<Mutant> selection) {
		super(TerminalRuleType.COMBINE_MUTANTS);
		this.generation = generation;
		this.selection = selection;
	}

	public SelectionOperation<Mutant> getSelection() {
		return selection;
	}

	public void setSelection(SelectionOperation<Mutant> selection) {
		this.selection = selection;
	}

	public AbstractHOMGeneration getGeneration() {
		return generation;
	}

	public void setGeneration(AbstractHOMGeneration generation) {
		this.generation = generation;
	}

	@Override
	public List<Mutant> doOperation(Solution input) {
		checkNotNull(selection, "No selection operation!");
		checkNotNull(generation, "No HOM generation operation!");
		List<Mutant> mutantsToCombine = selection.doOperation(new ArrayList<>(input.getMutants()));
		List<Mutant> generatedHoms = generation.doOperation(mutantsToCombine);
		input.getMutants().addAll(generatedHoms);
		return next(input);
	}

	@Override
	public boolean isSpecific() {
		boolean isSpecific = false;
		if (selection != null) {
			isSpecific = selection.isSpecific();
		}
		if (generation != null) {
			isSpecific = isSpecific || generation.isSpecific();
		}
		return isSpecific;
	}
}
