package org.mumue.mumue.configuration;

import javax.inject.Inject;

public class ConfigurationInitializer {
    private ApplicationConfiguration configuration;

    @Inject
    public ConfigurationInitializer(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    public ApplicationConfiguration initialize() {
        return configuration;
    }
}
