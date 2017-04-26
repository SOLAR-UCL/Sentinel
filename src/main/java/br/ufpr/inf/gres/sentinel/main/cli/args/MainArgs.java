package br.ufpr.inf.gres.sentinel.main.cli.args;

import com.beust.jcommander.Parameter;

/**
 * @author Giovani Guizzo
 */
public class MainArgs {

    @Parameter(names = {"--help", "-h"}, description = "Shows this message.")
    public boolean help = false;

}
