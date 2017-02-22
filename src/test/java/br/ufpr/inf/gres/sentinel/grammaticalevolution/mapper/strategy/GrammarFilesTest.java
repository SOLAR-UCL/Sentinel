package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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
		GrammarFiles.getGrammarPath("Unknown");
	}

}