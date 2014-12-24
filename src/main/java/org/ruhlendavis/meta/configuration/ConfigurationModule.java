package org.ruhlendavis.meta.configuration;

import com.google.inject.AbstractModule;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

public class ConfigurationModule extends AbstractModule {
    private final StartupConfiguration startupConfiguration;

    public ConfigurationModule(StartupConfiguration startupConfiguration) {
        this.startupConfiguration = startupConfiguration;
    }

    @Override
    protected void configure() {
        bind(StartupConfiguration.class).toInstance(startupConfiguration);
    }
}
