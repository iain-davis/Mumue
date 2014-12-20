package org.ruhlendavis.meta.acceptance;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.Main;
import org.ruhlendavis.meta.configuration.ConfigurationDefaults;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineAcceptanceTest {
    @Mock Listener listener;
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock StartupConfiguration startupConfiguration;
    @InjectMocks Main main;

    @Before
    public void beforeEach() {
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
    }

    @Test
    public void doNotRunForeverInTestMode() {
        main.run(listener, new CommandLineProvider("--test"));
    }

    @Test
    public void listenOnDefaultPort() {
        when(startupConfiguration.getTelnetPort()).thenReturn(ConfigurationDefaults.TELNET_PORT);
        main.run(listener, new CommandLineProvider("--test"));
        verify(listener).setPort(ConfigurationDefaults.TELNET_PORT);
    }
}
