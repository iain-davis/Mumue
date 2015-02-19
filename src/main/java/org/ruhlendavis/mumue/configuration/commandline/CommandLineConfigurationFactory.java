package org.ruhlendavis.mumue.configuration.commandline;

public class CommandLineConfigurationFactory {
    private CommandLineFactory commandLineFactory = new CommandLineFactory();

    public CommandLineConfiguration create(String... arguments) {
        return new CommandLineConfiguration(commandLineFactory.create(arguments));
    }
}