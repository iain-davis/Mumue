package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;

public class Configuration {
    private CommandLineConfiguration commandLineConfiguration;

    public Configuration(CommandLineConfiguration commandLineConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
    }

    public boolean isTest() {
        return commandLineConfiguration.isTest();
    }
}
