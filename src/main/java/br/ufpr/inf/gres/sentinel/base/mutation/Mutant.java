package br.ufpr.inf.gres.sentinel.base.mutation;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Mutant extends Program {

    protected Program originalProgram;
    protected SetUniqueList<Operator> operators;
    protected boolean equivalent = false;
    protected SetUniqueList<Mutant> constituentMutants;
    protected SetUniqueList<TestCase> killingTestCases;

}
