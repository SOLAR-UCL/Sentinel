package br.ufpr.inf.gres.sentinel.strategy.operation.impl.select.selection;

import br.ufpr.inf.gres.sentinel.strategy.operation.impl.group.AbstractGroupingOperation;
import com.google.common.math.DoubleMath;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @param <T>
 *
 * @author Giovani Guizzo
 */
@SuppressWarnings("ALL")
public class GroupSelectionOperation<T> extends SelectionOperation<T> {

	private AbstractGroupingOperation<T> groupingFunction;
	private SelectionOperation<T> selectionOperation;

	public GroupSelectionOperation() {
		super("Group Selection");
	}

	public AbstractGroupingOperation<T> getGroupingFunction() {
		return groupingFunction;
	}

	public void setGroupingFunction(AbstractGroupingOperation<T> groupingFunction) {
		this.groupingFunction = groupingFunction;
	}

	public SelectionOperation getSelectionOperation() {
		return selectionOperation;
	}

	public void setSelectionOperation(SelectionOperation selectionOperation) {
		this.selectionOperation = selectionOperation;
	}

	@Override
	public List<T> doOperation(List<T> input) {
		checkNotNull(selectionType, "No group selection type defined for group selection!");
		checkNotNull(groupingFunction, "No grouping function defined for group selection!");
		checkNotNull(selectionOperation, "No operator selection type defined for group selection!");
		checkArgument(percentage != 0D || quantity != 0,
					  "No quantity or percentage defined for group selection! " +
					  "Percentage: " +
					  percentage +
					  ". Quantity: " +
					  quantity +
					  ".");

		List<T> result = new ArrayList<>();
		List<List<T>> groups = groupingFunction.doOperation(input);

		if (groups.size() > 0) {
			int numberToSelect;
			if (percentage != 0D) {
				numberToSelect = DoubleMath.roundToInt(groups.size() * percentage, RoundingMode.CEILING);
			} else {
				numberToSelect = quantity;
			}

			if (sorter != null) {
				groups.sort(sorter);
			}

			groups = selectionType.selectItems(groups, numberToSelect);

			for (List<T> group : groups) {
				result.addAll(selectionOperation.doOperation(group));
			}
		}
		return result;
	}

	@Override
	public boolean isSpecific() {
		boolean isSpecific = super.isSpecific();
		if (groupingFunction != null) {
			isSpecific = isSpecific || groupingFunction.isSpecific();
		}
		if (selectionOperation != null) {
			isSpecific = isSpecific || selectionOperation.isSpecific();
		}
		return isSpecific;
	}

}
