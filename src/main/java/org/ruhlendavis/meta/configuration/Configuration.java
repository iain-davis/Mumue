package org.ruhlendavis.meta.configuration;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.meta.GlobalConstants;

public class Configuration {
    private final CommandLine commandLine;

    public Configuration(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public String getDatabaseUsername() {
        return GlobalConstants.DEFAULT_DATABASE_USERNAME;
    }

    public String getDatabasePassword() {
        return GlobalConstants.DEFAULT_DATABASE_PASSWORD;
    }

    public String getDatabasePath() {
        return GlobalConstants.DEFAULT_DATABASE_PATH;
    }

    public int getPort() {
        String port = commandLine.getOptionValue("port");
        return port == null ? GlobalConstants.DEFAULT_TELNET_PORT : Integer.parseInt(port);
    }

    public boolean isTest() {
        return commandLine.hasOption("test");
    }
}
