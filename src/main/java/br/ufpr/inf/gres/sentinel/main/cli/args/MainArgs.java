package br.ufpr.inf.gres.sentinel.main.cli.args;

import br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.strategy.GrammarFiles;
import br.ufpr.inf.gres.sentinel.main.cli.converter.SeparatorConverter;
import com.beust.jcommander.Parameter;

/**
 * @author Giovani Guizzo
 */
public class MainArgs {

    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

    @Parameter(names = {
        "--facade", "--integrationFacade", "--tool", "--mutationTool", "-m"
    },
            description = "The tool used to effectively generate the mutants. Available options: \"PIT\", \"muJava\", \"HG4HOM\" (same as muJava).")
    public String facade = "PIT";

    @Parameter(names = {"--workingDirectory", "-w"},
            description = "The working directory of Sentinel.",
            converter = SeparatorConverter.class)
    public String workingDirectory = System.getProperty("user.dir");

    @Parameter(names = {
        "--grammar", "--grammarFile", "-g"
    },
            description = "The grammar file used to interpret the strategy(ies). If this is informed, then Sentinel will search for the grammar in: /path/to/working/directory/grammars/.")
    public String grammarFile = GrammarFiles.DEFAULT_GRAMMAR_NO_HOMS;

    @Parameter(names = {
        "--grammarFilePath", "--grammarPath", "-gp"
    },
            description = "The grammar file path (relative to the working directory) used to interpret the strategy(ies). If both -g and -p are informed, -g will be given priority.",
            converter = SeparatorConverter.class)
    public String grammarFilePath = "grammars/default_grammar_no_homs.bnf";

}
