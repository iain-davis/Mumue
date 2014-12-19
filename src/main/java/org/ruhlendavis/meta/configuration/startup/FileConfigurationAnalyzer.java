package org.ruhlendavis.meta.configuration.startup;

public class FileConfigurationAnalyzer {
    public boolean isValid(StartupConfiguration startupConfiguration) {
        return !(startupConfiguration.getTelnetPort() < 1 || startupConfiguration.getTelnetPort() > 65535);
    }
}
