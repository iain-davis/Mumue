package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;

public class ConfigurationFactory {
    public Configuration createConfiguration(CommandLineProvider commandLineProvider) {
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLineProvider.get());
        return new Configuration(commandLineConfiguration, null, null);
    }
}
