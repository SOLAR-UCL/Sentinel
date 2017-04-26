package br.ufpr.inf.gres.sentinel.main.cli.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class ResultWrapper {

    private long executionTimeInMillis;
    private List<VariableLengthSolution<Integer>> result;

    public ResultWrapper() {
    }

    public ResultWrapper(long executionTimeInMillis, List<VariableLengthSolution<Integer>> result) {
        this.executionTimeInMillis = executionTimeInMillis;
        this.result = result;
    }

    public long getExecutionTimeInMillis() {
        return executionTimeInMillis;
    }

    public List<VariableLengthSolution<Integer>> getResult() {
        return result;
    }

    public void setExecutionTimeInMillis(long executionTimeInMillis) {
        this.executionTimeInMillis = executionTimeInMillis;
    }

    public void setResult(List<VariableLengthSolution<Integer>> result) {
        this.result = result;
    }

}
