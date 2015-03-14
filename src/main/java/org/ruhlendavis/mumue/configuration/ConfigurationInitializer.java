package org.ruhlendavis.mumue.configuration;

import javax.inject.Inject;

public class ConfigurationInitializer {
    private Configuration configuration;

    @Inject
    public ConfigurationInitializer(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration initialize() {
        return configuration;
    }
}
