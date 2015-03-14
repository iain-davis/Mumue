package org.ruhlendavis.mumue.configuration;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfigurationProvider;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationInitializerTest {
    private final String startupConfigurationPath = RandomStringUtils.randomAlphabetic(17);

    @Mock CommandLineConfiguration commandLineConfiguration;
    @Mock StartupConfiguration startupConfiguration;
    @Mock Configuration configuration;

    @Mock StartupConfigurationProvider startupConfigurationProvider;
    @Mock ConfigurationProvider configurationProvider;

    @InjectMocks ConfigurationInitializer configurationInitializer;

    @Before
    public void beforeEach() {
        when(commandLineConfiguration.getStartupConfigurationPath()).thenReturn(startupConfigurationPath);
        when(startupConfigurationProvider.get()).thenReturn(startupConfiguration);
        when(configurationProvider.create(eq(commandLineConfiguration), eq(startupConfiguration), any(OnlineConfiguration.class))).thenReturn(configuration);
    }

    @Test
    public void initializeConfigurationUsingProvidedConfigurations() {
        Configuration configuration = configurationInitializer.initialize();
        assertNotNull(configuration);
        verify(configurationProvider).create(eq(commandLineConfiguration), eq(startupConfiguration), any(OnlineConfiguration.class));
    }
}
