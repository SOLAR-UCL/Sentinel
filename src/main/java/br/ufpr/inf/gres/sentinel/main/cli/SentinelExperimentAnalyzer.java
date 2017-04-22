package br.ufpr.inf.gres.sentinel.main.cli;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.util.PointSolution;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelExperimentAnalyzer {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<String> files = Lists.newArrayList("output1.txt",
                "output2.txt",
                "output3.txt",
                "output4.txt",
                "output5.txt",
                "output6.txt",
                "output7.txt",
                "output8.txt",
                "output9.txt",
                "output10.txt");
        String dir = System.getProperty("user.dir") + File.separator + "training" + File.separator;
        PISAHypervolume hv = new PISAHypervolume(dir + "nonDominated.txt");
        for (String file : files) {
            List<PointSolution> solutions = new ArrayList<>();
            ArrayFront front = new ArrayFront(dir + file);
            solutions.addAll(FrontUtils.convertFrontToSolutionList(front));
            Files.append(hv.evaluate(solutions) + "\n", new File(dir + "hv.txt"), StandardCharsets.UTF_8);
        }
    }

}
