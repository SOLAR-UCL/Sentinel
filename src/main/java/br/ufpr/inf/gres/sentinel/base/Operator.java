package br.ufpr.inf.gres.sentinel.base;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 *
 * @author Giovani Guizzo
 */
public class Operator {

    protected String name;
    protected String type;
    protected SetUniqueList<Mutant> generatedMutants;

}
