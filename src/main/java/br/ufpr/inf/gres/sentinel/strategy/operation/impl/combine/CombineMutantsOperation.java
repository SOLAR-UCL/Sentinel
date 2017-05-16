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

    private AbstractHOMGeneration generation;
    private SelectionOperation<Mutant> selection;

    /**
     *
     */
    public CombineMutantsOperation() {
        super(TerminalRuleType.COMBINE_MUTANTS);
    }

    /**
     *
     * @param generation
     * @param selection
     */
    public CombineMutantsOperation(AbstractHOMGeneration generation, SelectionOperation<Mutant> selection) {
        super(TerminalRuleType.COMBINE_MUTANTS);
        this.generation = generation;
        this.selection = selection;
    }

    /**
     *
     * @param input
     * @return
     */
    @Override
    public List<Mutant> doOperation(Solution input) {
        checkNotNull(this.selection, "No selection operation!");
        checkNotNull(this.generation, "No HOM generation operation!");
        List<Mutant> mutantsToCombine = this.selection.doOperation(new ArrayList<>(input.getMutants()));
        List<Mutant> generatedHoms = this.generation.doOperation(mutantsToCombine);
        input.getMutants().addAll(generatedHoms);
        return this.next(input);
    }

    /**
     *
     * @return
     */
    public AbstractHOMGeneration getGeneration() {
        return this.generation;
    }

    /**
     *
     * @param generation
     */
    public void setGeneration(AbstractHOMGeneration generation) {
        this.generation = generation;
    }

    /**
     *
     * @return
     */
    public SelectionOperation<Mutant> getSelection() {
        return this.selection;
    }

    /**
     *
     * @param selection
     */
    public void setSelection(SelectionOperation<Mutant> selection) {
        this.selection = selection;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSpecific() {
        boolean isSpecific = false;
        if (this.selection != null) {
            isSpecific = this.selection.isSpecific();
        }
        if (this.generation != null) {
            isSpecific = isSpecific || this.generation.isSpecific();
        }
        return isSpecific;
    }
}
