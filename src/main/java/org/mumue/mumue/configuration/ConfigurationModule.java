package org.mumue.mumue.configuration;

import java.util.Collection;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ConfigurationModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    Collection<PortConfiguration> providesPortConfigurations(PortConfigurationRepository repository) {
        return repository.getAll();
    }
}
