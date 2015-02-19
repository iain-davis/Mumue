package org.ruhlendavis.mumue.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
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
import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfigurationFactory;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfigurationFactory;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationInitializerTest {
    private final String startupConfigurationPath = RandomStringUtils.randomAlphabetic(17);

    @Mock CommandLineConfiguration commandLineConfiguration;
    @Mock StartupConfiguration startupConfiguration;
    @Mock Configuration configuration;

    @Mock CommandLineConfigurationFactory commandLineConfigurationFactory;
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock ConfigurationProvider configurationProvider;

    @InjectMocks ConfigurationInitializer configurationInitializer;

    @Before
    public void beforeEach() {
        when(commandLineConfigurationFactory.create(anyVararg())).thenReturn(commandLineConfiguration);
        when(commandLineConfiguration.getStartupConfigurationPath()).thenReturn(startupConfigurationPath);
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
        when(configurationProvider.create(eq(commandLineConfiguration), eq(startupConfiguration), any(OnlineConfiguration.class))).thenReturn(configuration);
    }

    @Test
    public void createCommandLineConfigurationWithoutArguments() {
        configurationInitializer.initialize();
        verify(commandLineConfigurationFactory).create();
    }

    @Test
    public void createCommandLineConfigurationWithArgument() {
        String argument = RandomStringUtils.randomAlphabetic(17);
        configurationInitializer.initialize(argument);
        verify(commandLineConfigurationFactory).create(argument);
    }

    @Test
    public void createCommandLineConfigurationWithArguments() {
        String argument1 = RandomStringUtils.randomAlphabetic(17);
        String argument2 = RandomStringUtils.randomAlphabetic(17);
        String argument3 = RandomStringUtils.randomAlphabetic(17);
        configurationInitializer.initialize(argument1, argument2, argument3);
        verify(commandLineConfigurationFactory).create(argument1, argument2, argument3);
    }

    @Test
    public void useStartupConfigurationPathProvidedByCommandLineConfiguration() {
        configurationInitializer.initialize();
        verify(startupConfigurationFactory).create(startupConfigurationPath);
    }

    @Test
    public void initializeConfigurationUsingProvidedConfigurations() {
        Configuration configuration = configurationInitializer.initialize();
        assertNotNull(configuration);
        verify(configurationProvider).create(eq(commandLineConfiguration), eq(startupConfiguration), any(OnlineConfiguration.class));
    }
}
