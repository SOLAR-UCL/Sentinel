package br.ufpr.inf.gres.sentinel.main.cli;

import java.io.File;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelTest {

    public SentinelTest() {
    }

    @Test
    public void testSentinel() throws Exception {
        String[] args = new String[]{"train",
            "--maxEvaluations", "2",
            "--populationSize", "2",
            "--trainingRuns", "1",
            "--numberOfConventionalRuns", "1",
            "-tp", "Triangle;src/test/resources;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;src/test/resources"};
        Sentinel.main(args);
        File result = new File("training/Experiment/result_1.json");
        Assert.assertTrue(result.exists());
        Assert.assertTrue(result.length() > 0);
        result.delete();
        result.getParentFile().delete();
    }

    @Test
    public void testSentinel2() throws Exception {
        String[] args = new String[]{"train",
            "--maxEvaluations", "2",
            "--populationSize", "2",
            "--trainingRuns", "1",
            "--numberOfConventionalRuns", "1",
            "--cached",
            "--storeCacheInFile", "false",
            "--verbose",
            "-tp", "Triangle;src/test/resources;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;src/test/resources"};
        Sentinel.main(args);

        File result = new File("training/Experiment/result_1.json");
        Assert.assertTrue(result.exists());
        Assert.assertTrue(result.length() > 0);
        result.delete();
        result.getParentFile().delete();

        File cache = new File("training/.cache/Triangle.json");
        Assert.assertFalse(cache.exists());
    }

    @Test
    public void testSentinelCache() throws Exception {
        String[] args = new String[]{"cache",
            "--numberOfRuns", "1",
            "--verbose",
            "-tp", "Triangle;src/test/resources;br.ufpr.inf.gres.TriTyp*;br.ufpr.inf.gres.TriTypTest*;;src/test/resources"};
        Sentinel.main(args);

        File cache = new File(".cache/Triangle.json");
        Assert.assertTrue(cache.exists());
        cache.delete();
        cache.getParentFile().delete();
    }

    @Test
    @Ignore
    public void testSentinelAnalysis() throws Exception {
        String[] args = new String[]{"analyse",
            "--plotWidth", "800",
            "--plotHeight", "600",
            "--axisLabels", "Time", "Score",
            "--inputDirectory", "testing/wire-2.2.0/",
            "--outputDirectory", "testinganalysis/wire-2.2.0/"};
        Sentinel.main(args);
    }

    @Test
    @Ignore
    public void testSentinelTest() throws Exception {
        String[] args = new String[]{"test"};
        Sentinel.main(args);
    }

    @Test
    public void testSentinelHelp() throws Exception {
        String[] args = new String[]{"unknownCommand"};
        Sentinel.main(args);

        args = new String[]{"--unknownArg"};
        Sentinel.main(args);

        args = new String[]{};
        Sentinel.main(args);

        args = new String[]{"-h"};
        Sentinel.main(args);

        args = new String[]{"train", "-h"};
        Sentinel.main(args);

        args = new String[]{"test", "-h"};
        Sentinel.main(args);

        args = new String[]{"cache", "-h"};
        Sentinel.main(args);

        args = new String[]{"analyse", "-h"};
        Sentinel.main(args);
    }

}
