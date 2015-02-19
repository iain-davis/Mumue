package org.ruhlendavis.mumue.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationProviderTest {
    @Mock CommandLineConfiguration commandLineConfiguration;
    @Mock StartupConfiguration startupConfiguration;
    @Mock OnlineConfiguration onlineConfiguration;
    private final ConfigurationProvider configurationProvider = new ConfigurationProvider();

    @Before
    public void beforeEach() {
        configurationProvider.destroy();
    }

    @Test
    public void createReturnsConfiguration() {
        Configuration configuration = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        assertNotNull(configuration);
        assertThat(configuration, is(instanceOf(Configuration.class)));
    }

    @Test
    public void multipleCreatesReturnSameConfiguration() {
        Configuration configuration1 = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        Configuration configuration2 = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);

        assertThat(configuration1, is(sameInstance(configuration2)));
    }

    @Test
    public void getReturnsConfiguration() {
        configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        Configuration configuration = ConfigurationProvider.get();
        assertNotNull(configuration);
        assertThat(configuration, is(instanceOf(Configuration.class)));
    }

    @Test
    public void getReturnsSameConfiguration() {
        Configuration configuration1 = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        Configuration configuration2 = ConfigurationProvider.get();

        assertThat(configuration1, is(sameInstance(configuration2)));
    }

    @Test
    public void createUsesProvidedCommandLineConfiguration() {
        Configuration configuration = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);

        when(commandLineConfiguration.isTest()).thenReturn(true);
        assertTrue(configuration.isTest());

        when(commandLineConfiguration.isTest()).thenReturn(false);
        assertFalse(configuration.isTest());
    }

    @Test
    public void createUsesProvidedStartupConfiguration() {
        int port = RandomUtils.nextInt(1024, 65536);
        Configuration configuration = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        when(startupConfiguration.getTelnetPort()).thenReturn(port);

        assertThat(configuration.getTelnetPort(), equalTo(port));
    }

    @Test
    public void createUsesProvidedOnlineConfiguration() {
        Configuration configuration = configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        String serverLocale = RandomStringUtils.randomAlphabetic(5);
        when(onlineConfiguration.getServerLocale()).thenReturn(serverLocale);

        assertThat(configuration.getServerLocale(), equalTo(serverLocale));
    }
}
