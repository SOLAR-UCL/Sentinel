package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.operation;

import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection.SelectionOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giovani Guizzo
 */
public abstract class AbstractSelectOperation<T> extends Operation<Solution, List<T>> {

	private SelectionOperation<T> selection;

	public AbstractSelectOperation(String name, SelectionOperation<T> selection) {
		super(name);
		this.selection = selection;
	}

	public AbstractSelectOperation(String name) {
		super(name);
	}

	public SelectionOperation<T> getSelection() {
		return selection;
	}

	public void setSelection(SelectionOperation<T> selection) {
		this.selection = selection;
	}

	@Override
	public boolean isSpecific() {
		return selection != null && selection.isSpecific();
	}

	@Override
	public List<T> doOperation(Solution solution) {
		List<T> listToRetain = obtainList(solution);
		if (selection != null) {
			listToRetain.retainAll(selection.doOperation(new ArrayList<>(listToRetain)));
		}
		return next(solution);
	}

	protected abstract List<T> obtainList(Solution solution);

}
