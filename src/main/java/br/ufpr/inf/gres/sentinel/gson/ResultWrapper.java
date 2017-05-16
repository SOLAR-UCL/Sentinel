package br.ufpr.inf.gres.sentinel.gson;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.algorithm.representation.VariableLengthSolution;
import java.util.Collections;
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

    /**
     *
     * @return
     */
    public long getExecutionTimeInMillis() {
        return this.executionTimeInMillis;
    }

    /**
     *
     * @return
     */
    public String getGrammarFile() {
        return this.grammarFile;
    }

    /**
     *
     * @return
     */
    public List<String> getObjectiveFunctions() {
        return Collections.unmodifiableList(objectiveFunctions);
    }

    /**
     *
     * @return
     */
    public List<VariableLengthSolution<Integer>> getResult() {
        return Collections.unmodifiableList(result);
    }

    /**
     *
     * @return
     */
    public int getRunNumber() {
        return this.runNumber;
    }

    /**
     *
     * @return
     */
    public String getSession() {
        return this.session;
    }

    /**
     *
     * @param executionTimeInMillis
     * @return
     */
    public ResultWrapper setExecutionTimeInMillis(long executionTimeInMillis) {
        this.executionTimeInMillis = executionTimeInMillis;
        return this;
    }

    /**
     *
     * @param grammarFile
     * @return
     */
    public ResultWrapper setGrammarFile(String grammarFile) {
        this.grammarFile = grammarFile;
        return this;
    }

    /**
     *
     * @param objectiveFunctions
     * @return
     */
    public ResultWrapper setObjectiveFunctions(List<String> objectiveFunctions) {
        this.objectiveFunctions = objectiveFunctions;
        return this;
    }

    /**
     *
     * @param result
     * @return
     */
    public ResultWrapper setResult(List<VariableLengthSolution<Integer>> result) {
        this.result = result;
        return this;
    }

    /**
     *
     * @param runNumber
     * @return
     */
    public ResultWrapper setRunNumber(int runNumber) {
        this.runNumber = runNumber;
        return this;
    }

    /**
     *
     * @param session
     * @return
     */
    public ResultWrapper setSession(String session) {
        this.session = session;
        return this;
    }

}
