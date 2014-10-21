package org.ruhlendavis.meta.configuration;

public class ConfigurationAnalyzer {
    public boolean isValid(Configuration configuration) {
        if (configuration.getTelnetPort() < 1 || configuration.getTelnetPort() > 65535) {
            return false;
        }
        return true;
    }
}
