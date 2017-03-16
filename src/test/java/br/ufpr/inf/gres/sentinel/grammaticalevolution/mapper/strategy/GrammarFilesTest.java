package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import java.io.File;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Giovani Guizzo
 */
public class GrammarFilesTest {

    private String workingDirectory;

    @Before
    public void setUp() {
        this.workingDirectory = GrammarFiles.getWorkingDirectory();
    }

    @After
    public void tearDown() {
        GrammarFiles.setWorkingDirectory(this.workingDirectory);
    }

    @Test
    public void setWorkingDirectory() throws Exception {
        GrammarFiles.setWorkingDirectory("test");
        String defaultGrammarPath = GrammarFiles.getDefaultGrammarPath();
        assertEquals("test" + File.separator + "grammars" + File.separator + "default_grammar.bnf", defaultGrammarPath);
    }

    @Test(expected = Exception.class)
    public void getGrammarPath() throws Exception {
        GrammarFiles.setWorkingDirectory("test");
        String defaultGrammarPath = GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR);
        assertEquals("test" + File.separator + "grammars" + File.separator + "default_grammar.bnf", defaultGrammarPath);
        defaultGrammarPath = GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR_NO_HOMS);
        assertEquals("test" + File.separator + "grammars" + File.separator + "default_grammar_no_homs.bnf", defaultGrammarPath);
        GrammarFiles.getGrammarPath("Unknown");
    }

    @Test
    public void testGetGrammarPathFromWorkingDirectory() {
        GrammarFiles.setWorkingDirectory("test");
        String grammar = GrammarFiles.getGrammarPathFromWorkingDirectory("Test2.bnf");
        assertEquals("test" + File.separator + "grammars" + File.separator + "Test2.bnf", grammar);
    }

}
