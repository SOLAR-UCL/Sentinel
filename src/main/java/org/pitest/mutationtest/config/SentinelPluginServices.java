/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pitest.mutationtest.config;

/**
 *
 * @author Giovani
 */
public class SentinelPluginServices extends PluginServices {

    public SentinelPluginServices(ClassLoader loader) {
        super(loader);
    }

//    @Override
//    Collection<? extends TestPrioritiserFactory> findTestPrioritisers() {
//        return Lists.newArrayList(new NoPrioritisationTestPrioritiserFactory());
//    }
}
