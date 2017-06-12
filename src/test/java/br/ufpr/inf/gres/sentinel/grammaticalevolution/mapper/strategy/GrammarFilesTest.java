package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Giovani Guizzo
 */
public class GrammarFilesTest {

    private String workingDirectory;

    @Test(expected = Exception.class)
    public void getGrammarPath() throws Exception {
        GrammarFiles.setWorkingDirectory("test");
        String defaultGrammarPath = GrammarFiles.getGrammarPath(GrammarFiles.DEFAULT_GRAMMAR);
        assertEquals("test" + File.separator + "grammars" + File.separator + "default_grammar.bnf", defaultGrammarPath);
        GrammarFiles.getGrammarPath("Unknown");
    }

    @Before
    public void setUp() {
        this.workingDirectory = GrammarFiles.getWorkingDirectory();
    }

    @Test
    public void setWorkingDirectory() throws Exception {
        GrammarFiles.setWorkingDirectory("test");
        String defaultGrammarPath = GrammarFiles.getDefaultGrammarPath();
        assertEquals("test" + File.separator + "grammars" + File.separator + "default_grammar.bnf", defaultGrammarPath);
    }

    @After
    public void tearDown() {
        GrammarFiles.setWorkingDirectory(this.workingDirectory);
    }

    @Test
    public void testGetGrammarPathFromWorkingDirectory() {
        GrammarFiles.setWorkingDirectory("test");
        String grammar = GrammarFiles.getGrammarPathFromWorkingDirectory("Test2.bnf");
        assertEquals("test" + File.separator + "Test2.bnf", grammar);
    }

}
