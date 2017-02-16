package br.ufpr.inf.gres.sentinel.main;

import com.beust.jcommander.JCommander;

/**
 * Main class.
 *
 * @author Giovani Guizzo
 */
public class Sentinel {

	public static void main(String[] args) {
		CommandLineProperties properties = new CommandLineProperties();
		JCommander commander = new JCommander(properties, args);
		if (properties.help) {
			commander.usage();
		}
	}

}
