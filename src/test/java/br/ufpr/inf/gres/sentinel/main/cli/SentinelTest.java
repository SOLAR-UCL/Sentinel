package br.ufpr.inf.gres.sentinel.main.cli;

import java.io.File;
import org.junit.Assert;
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
        String[] args = new String[]{"train", "--maxEvaluations", "1", "--populationSize", "1", "--trainingRuns", "1"};
        Sentinel.main(args);
        File result = new File("training/result.json");
        Assert.assertTrue(result.exists());
        Assert.assertTrue(result.length() > 0);
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
    }

    @Test
    public void testSentinelAnalysis() throws Exception {
        String[] args = new String[]{"analyse", "--inputDirectory", "training/GE"};
        Sentinel.main(args);
    }

}
