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

    private Operation<Solution, List<Mutant>> firstOperation;

    public Operation<Solution, List<Mutant>> getFirstOperation() {
        return firstOperation;
    }

    public void setFirstOperation(Operation<Solution, List<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    public Strategy() {
    }

    public Strategy(Operation<Solution, List<Mutant>> firstOperation) {
        this.firstOperation = firstOperation;
    }

    public List<Mutant> run() {
        if (firstOperation != null) {
            return firstOperation.doOperation(new Solution());
        }
        return SetUniqueList.setUniqueList(Collections.emptyList());
    }

    @Override
    public String toString() {
        List<String> ops = new ArrayList<>();
        Operation<Solution, List<Mutant>> temp = firstOperation;
        while (temp != null) {
            ops.add(temp.getName());
            temp = temp.getSuccessor();
        }
        return Joiner.on(" - ").join(ops);
    }
}
