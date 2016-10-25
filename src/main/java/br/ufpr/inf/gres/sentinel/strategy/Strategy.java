package br.ufpr.inf.gres.sentinel.strategy;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.strategy.operation.Operation;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 * Created by Giovani Guizzo on 24/10/2016.
 */
public class Strategy {

    private Operation firstOperation;

    public Operation getFirstOperation() {
        return firstOperation;
    }

    public void setFirstOperation(Operation firstOperation) {
        this.firstOperation = firstOperation;
    }

    public Strategy() {
    }

    public Strategy(Operation firstOperation) {
        this.firstOperation = firstOperation;
    }

    public SetUniqueList<Mutant> run() {
        if (firstOperation != null) {
            return firstOperation.doOperation(new Solution());
        }
        return SetUniqueList.setUniqueList(Collections.emptyList());
    }

    @Override
    public String toString() {
        List<String> ops = new ArrayList<>();
        Operation temp = firstOperation;
        while (temp != null) {
            ops.add(temp.getName());
            temp = temp.getSuccessor();
        }
        return Joiner.on(" - ").join(ops);
    }
}
