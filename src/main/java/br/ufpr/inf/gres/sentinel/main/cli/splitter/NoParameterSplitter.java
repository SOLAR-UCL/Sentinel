/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.cli.splitter;

import com.beust.jcommander.converters.IParameterSplitter;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Giovani
 */
public class NoParameterSplitter implements IParameterSplitter {

    @Override
    public List<String> split(String value) {
        return Lists.newArrayList(value);
    }

}
