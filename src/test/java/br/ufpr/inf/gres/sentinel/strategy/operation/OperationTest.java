package br.ufpr.inf.gres.sentinel.strategy.operation;

import br.ufpr.inf.gres.sentinel.base.mutation.Mutant;
import br.ufpr.inf.gres.sentinel.base.solution.Solution;
import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.factory.TerminalRuleType;
import br.ufpr.inf.gres.sentinel.strategy.operation.impl.defaults.NewBranchOperation;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OperationTest {

    public OperationTest() {
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Operation instance = getDualOperationChain();
        String expResult = "TestOperation";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testToStringComplete() {
        System.out.println("toStringComplete");
        Operation instance = getDualOperationChain();
        String expResult = "1.TestOperation - 2.TestOperation2";
        String result = instance.toStringComplete();
        assertEquals(expResult, result);
    }

    @Test
    public void testToStringCompleteSingleOperation() {
        System.out.println("toStringComplete");
        Operation instance = new OperationStub("TestOperation", false);
        String expResult = "1.TestOperation";
        String result = instance.toStringComplete();
        assertEquals(expResult, result);
    }

    @Test
    public void testToStringCompleteWithNewBranch() {
        System.out.println("toStringCompleteNewBranch");
        Operation instance = getComplexTestOperationChain();

        String expResult = "1.TestOperation - 2.TestOperation2 - 3." + TerminalRuleType.NEW_BRANCH + " - 4.TestOperation3\n"
                + "\t3.1." + TerminalRuleType.NEW_BRANCH + " - 3.2." + TerminalRuleType.NEW_BRANCH + " - 3.3.TestOperation5\n"
                + "\t3.2.1.TestOperation6\n"
                + "\t3.1.1.TestOperation4";
        String result = instance.toStringComplete();
        assertEquals(expResult, result);
    }

    public static Operation getDualOperationChain() {
        Operation instance = new OperationStub("TestOperation", false);
        instance.setSuccessor(new OperationStub("TestOperation2", false));
        return instance;
    }

    public static Operation getComplexTestOperationChain() {
        Operation instance = new OperationStub("TestOperation", false);
        Operation instance2 = new OperationStub("TestOperation2", false);
        instance.setSuccessor(instance2);
        NewBranchOperation newBranch = new NewBranchOperation();
        instance2.setSuccessor(newBranch);
        newBranch.setSuccessor(new OperationStub("TestOperation3", false));
        NewBranchOperation newBranch2 = new NewBranchOperation();
        newBranch.setSecondSuccessor(newBranch2);
        NewBranchOperation newBranch3 = new NewBranchOperation();
        newBranch2.setSuccessor(newBranch3);
        newBranch2.setSecondSuccessor(new OperationStub("TestOperation4", false));
        newBranch3.setSuccessor(new OperationStub("TestOperation5", false));
        newBranch3.setSecondSuccessor(new OperationStub("TestOperation6", false));
        return instance;
    }

    public static class OperationStub extends Operation<Solution, List<Mutant>> {

        public OperationStub(String name, boolean specific) {
            super(name, specific);
        }

        @Override
        public List<Mutant> doOperation(Solution input) {
            input.getMutants().add(new Mutant(name + " executed!", null, null));
            List<Mutant> result = next(input);
            return result == null ? input.getMutants() : result;
        }
    }

}
