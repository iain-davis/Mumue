package org.mumue.mumue.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.junit.jupiter.api.Test;

class ConfigurationModuleTest {
    private static final PortConfigurationRepository repository = mock(PortConfigurationRepository.class);
    private static final Random RANDOM = new Random();
    private final Injector injector = Guice.createInjector(
            new RepositoryModule(),
            new ConfigurationModule()
    );

    @Test
    void providesAcceptorNeverReturnsNull() {
        ConfigurationModule module = new ConfigurationModule();
        assertThat(module.providesPortConfigurations(repository), notNullValue());
    }

    @Test
    void providesAcceptorListInstantiatesList() {
        injector.getInstance(new Key<Collection<PortConfiguration>>() {
        });
    }

    @Test
    void providesAcceptorListIsPopulated() {
        PortConfiguration expected = new PortConfiguration();
        expected.setPort(RANDOM.nextInt(1000));
        expected.setSupportsMenus(RANDOM.nextBoolean());
        expected.setType(PortType.SSH);
        when(repository.getAll()).thenReturn(Collections.singletonList(expected));

        Collection<PortConfiguration> configurations = injector.getInstance(new Key<>() {
        });

        assertThat(configurations.size(), equalTo(1));
        PortConfiguration configuration = configurations.iterator().next();
        assertThat(configuration.getPort(), equalTo(expected.getPort()));
        assertThat(configuration.isSupportsMenus(), equalTo(expected.isSupportsMenus()));
        assertThat(configuration.getType(), equalTo(expected.getType()));
    }

    private static class RepositoryModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(PortConfigurationRepository.class).toInstance(repository);
        }
    }
}
