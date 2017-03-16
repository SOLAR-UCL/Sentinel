package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import com.google.common.base.CharMatcher;
import java.io.File;

/**
 * @author Giovani Guizzo
 */
public class GrammarFiles {

    public static final String DEFAULT_GRAMMAR = "default";

    private static String WORKING_DIRECTORY = System.getProperty("user.dir");

    private GrammarFiles() {
    }

    public static String getWorkingDirectory() {
        return WORKING_DIRECTORY;
    }

    public static void setWorkingDirectory(String workingDirectory) {
        WORKING_DIRECTORY = CharMatcher.anyOf("\\/").replaceFrom(workingDirectory, File.separator);
    }

    public static String getDefaultGrammarPath() {
        return getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR);
    }

    public static String getGrammarPath(String grammarName) {
        String directory = WORKING_DIRECTORY + File.separator + "grammars" + File.separator;
        switch (grammarName.toLowerCase()) {
            case DEFAULT_GRAMMAR:
                return directory + "default_grammar.bnf";
            default:
                throw new IllegalArgumentException("Grammar " + grammarName + " not recognized.");
        }
    }

    public static String getGrammarPathFromWorkingDirectory(String grammarFileName) {
        return WORKING_DIRECTORY + File.separator + "grammars" + File.separator + grammarFileName;
    }
}
