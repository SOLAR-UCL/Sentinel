package br.ufpr.inf.gres.sentinel.main.cli;

import br.ufpr.inf.gres.sentinel.main.cli.args.AnalysisArgs;
import br.ufpr.inf.gres.sentinel.main.cli.gson.GsonUtil;
import br.ufpr.inf.gres.sentinel.main.cli.gson.ResultWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Giovani Guizzo
 */
public class SentinelAnalysis {

    public static void analyse(AnalysisArgs analysisArgs, String[] args) throws IOException {
        File trainingDirectory = new File(analysisArgs.workingDirectory + File.separator + analysisArgs.trainingDirectory);
        File outputDirectory = new File(analysisArgs.workingDirectory + File.separator + analysisArgs.outputDirectory);

        HashMap<String, File> groups = new HashMap<>();
        for (String session : analysisArgs.sessions) {
            groups.put(session, new File(trainingDirectory.getAbsolutePath() + File.separator + session));
        }

        GsonUtil gson = new GsonUtil();
        ListMultimap<String, ResultWrapper> groupResults = ArrayListMultimap.create();
        for (String session : analysisArgs.sessions) {
            File groupDir = groups.get(session);
            Iterable<File> jsonFiles = Iterables.filter(Files.fileTreeTraverser().children(groupDir), file -> Files.getFileExtension(file.getName()).equals("json"));
            for (File jsonFile : jsonFiles) {
                ResultWrapper result = gson.fromJson(jsonFile);
                groupResults.put(session, result);
            }
        }
    }

}
