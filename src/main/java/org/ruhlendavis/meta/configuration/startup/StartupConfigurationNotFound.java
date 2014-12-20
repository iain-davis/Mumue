package org.ruhlendavis.meta.configuration.startup;

public class StartupConfigurationNotFound extends RuntimeException {
    public StartupConfigurationNotFound(String message) {
        super(message);
    }
}
