package org.ruhlendavis.meta.configuration;

import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.meta.GlobalConstants;

public class Configuration {
    private final CommandLine commandLine;

    public Configuration(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public boolean isTest() {
        return commandLine.hasOption("test");
    }

    public int getPort() {
        String port = commandLine.getOptionValue("port");
        return port == null ? GlobalConstants.DEFAULT_TELNET_PORT : Integer.parseInt(port);
    }
}
