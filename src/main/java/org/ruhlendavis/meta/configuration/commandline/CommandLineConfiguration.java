package org.ruhlendavis.meta.configuration.commandline;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class CommandLineConfiguration {
    private final CommandLine commandLine;

    public CommandLineConfiguration(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean isTest() {
        return commandLine.hasOption(CommandLineOptionName.TEST);
    }

    public String getStartupConfigurationPath() {
        if (commandLine.hasOption(CommandLineOptionName.STARTUP_CONFIGURATION_PATH)) {
            return commandLine.getOptionValue(CommandLineOptionName.STARTUP_CONFIGURATION_PATH);
        }
        return ConfigurationDefaults.CONFIGURATION_PATH;
    }
}
