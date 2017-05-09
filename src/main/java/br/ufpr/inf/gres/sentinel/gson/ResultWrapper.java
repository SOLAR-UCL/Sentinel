package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.List;

/**
 *
 * @author Giovani Guizzo
 */
public class ResultWrapper {

    private String session;
    private int runNumber;
    private String grammarFile;
    private long executionTimeInMillis;
    private List<String> objectiveFunctions;
    private List<VariableLengthSolution<Integer>> result;

    public long getExecutionTimeInMillis() {
        return executionTimeInMillis;
    }

    public ResultWrapper setExecutionTimeInMillis(long executionTimeInMillis) {
        this.executionTimeInMillis = executionTimeInMillis;
        return this;
    }

    public List<VariableLengthSolution<Integer>> getResult() {
        return result;
    }

    public ResultWrapper setResult(List<VariableLengthSolution<Integer>> result) {
        this.result = result;
        return this;
    }

    public String getSession() {
        return session;
    }

    public ResultWrapper setSession(String session) {
        this.session = session;
        return this;
    }

    public String getGrammarFile() {
        return grammarFile;
    }

    public ResultWrapper setGrammarFile(String grammarFile) {
        this.grammarFile = grammarFile;
        return this;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public ResultWrapper setRunNumber(int runNumber) {
        this.runNumber = runNumber;
        return this;
    }

    public List<String> getObjectiveFunctions() {
        return objectiveFunctions;
    }

    public ResultWrapper setObjectiveFunctions(List<String> objectiveFunctions) {
        this.objectiveFunctions = objectiveFunctions;
        return this;
    }

}
