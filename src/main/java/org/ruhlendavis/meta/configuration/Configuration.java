package org.ruhlendavis.meta.configuration;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.meta.constants.Defaults;
import org.ruhlendavis.meta.constants.OptionName;

public class Configuration {
    private final CommandLine commandLine;

    public Configuration(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public String getDatabaseUsername() {
        return Defaults.DATABASE_USERNAME;
    }

    public String getDatabasePassword() {
        return Defaults.DATABASE_PASSWORD;
    }

    public String getDatabasePath() {
        return Defaults.DATABASE_PATH;
    }

    public int getPort() {
        String port = commandLine.getOptionValue(OptionName.PORT);
        return port == null ? Defaults.TELNET_PORT : Integer.parseInt(port);
    }

    public boolean isTest() {
        return commandLine.hasOption(OptionName.TEST);
    }

    public String getServerLocale() {
        return Defaults.SERVER_LOCALE;
    }
}
