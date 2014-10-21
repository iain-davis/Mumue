package org.ruhlendavis.meta.configuration;

public class ConfigurationAnalyzer {
    public boolean isValid(Configuration configuration) {
        return !(configuration.getTelnetPort() < 1 || configuration.getTelnetPort() > 65535);
    }
}
