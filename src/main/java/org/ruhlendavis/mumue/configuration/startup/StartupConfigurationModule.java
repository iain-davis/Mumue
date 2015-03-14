package org.ruhlendavis.mumue.configuration.startup;

import com.google.inject.AbstractModule;

public class StartupConfigurationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StartupConfiguration.class).toProvider(StartupConfigurationProvider.class);
    }
}
