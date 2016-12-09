package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giovani Guizzo
 */
public class GrammarFiles {

    private static String DEFAULT_GRAMMAR;

    static {
        try {
            DEFAULT_GRAMMAR = ClassLoader.getSystemResource("default_grammar.bnf").toURI().getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(GrammarFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private GrammarFiles() {
    }

    public static String getDefaultGrammarPath() {
        return DEFAULT_GRAMMAR;
    }

}
