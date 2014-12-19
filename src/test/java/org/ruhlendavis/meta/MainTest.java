package org.ruhlendavis.meta;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock StartupConfiguration startupConfiguration;
    @Mock Listener listener;
    @InjectMocks Main main;

    @Test
    public void doNotRunForeverInTest() {
        main.run(listener, new CommandLineProvider("--test"));
    }

    @Test
    public void loadStartupConfiguration() {
        main.run(listener, new CommandLineProvider("--test"));
        verify(startupConfiguration).load(ConfigurationDefaults.CONFIGURATION_PATH);
    }
}
