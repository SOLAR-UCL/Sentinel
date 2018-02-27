/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.sentinel.main.script;

import br.ufpr.inf.gres.sentinel.indictaors.IndicatorFactory;
import br.ufpr.inf.gres.sentinel.main.cli.Sentinel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 *
 * @author Giovani
 */
public class MultipleGroupsAnalysis {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, Exception {
        String inputDir = "../systems/testing/";
        String outputDir = "../systems/analysis/testing/";

        HashSet<String> systems = new LinkedHashSet<>();
        systems.add("commons-beanutils-1.8.0");
        systems.add("commons-codec-1.11");
        systems.add("commons-collections-3.0");
        systems.add("commons-lang-3.0");
        systems.add("commons-validator-1.4.0");
        systems.add("jfreechart-1.0.0");
        systems.add("jgrapht-0.9.0");
        systems.add("joda-time-2.8");
        systems.add("ognl-3.1");
        systems.add("wire-2.0.0");

        for (String system : systems) {
            args = new String[]{
                "analyse",
                "-id", inputDir + system,
                "-od", outputDir + system,
                "--printIntermediateFiles",
                "--indicators", IndicatorFactory.IGD
            };

            Sentinel.main(args);
        }
    }

}
