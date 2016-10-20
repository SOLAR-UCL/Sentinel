package br.ufpr.inf.gres.sentinel.operation;

import br.ufpr.inf.gres.sentinel.base.Mutant;
import br.ufpr.inf.gres.sentinel.base.Solution;
import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public abstract class Operation {

    protected Operation successor;
    protected String name;
    protected boolean specific = false;

    public abstract SetUniqueList<Mutant> doOperation(Solution solution);

    public SetUniqueList<Mutant> next(Solution solution) {
        if (successor != null) {
            return successor.doOperation(solution);
        }
        return null;
    }

}
