package org.ruhlendavis.mumue.configuration.startup;

public class StartupConfigurationAnalyzer {
    public boolean isValid(StartupConfiguration startupConfiguration) {
        return !(startupConfiguration.getTelnetPort() < 1 || startupConfiguration.getTelnetPort() > 65535);
    }
}
