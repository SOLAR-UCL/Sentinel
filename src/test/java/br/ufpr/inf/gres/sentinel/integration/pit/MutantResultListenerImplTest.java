/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.integration.pit;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giovani
 */
public class MutantResultListenerImplTest {

    public MutantResultListenerImplTest() {
    }

    @Test
    public void testGetResults() {
        MutantResultListenerImpl listener = new MutantResultListenerImpl();
        Assert.assertTrue(listener.getResults().isEmpty());
    }

}
