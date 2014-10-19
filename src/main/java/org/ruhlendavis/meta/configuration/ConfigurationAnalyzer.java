package org.ruhlendavis.meta.configuration;

public class ConfigurationAnalyzer {
    public boolean isValid(Configuration configuration) {
        if (configuration.getPort() < 1 || configuration.getPort() > 65535) {
            return false;
        }
        return true;
    }
}
