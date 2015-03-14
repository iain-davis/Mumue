package org.ruhlendavis.mumue.configuration.commandline;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

@Singleton
public class CommandLineConfiguration {
    private final CommandLine commandLine;

    @Inject
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
