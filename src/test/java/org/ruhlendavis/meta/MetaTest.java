package org.ruhlendavis.meta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;
import org.ruhlendavis.meta.configuration.TestConstants;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationNotFound;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MetaTest {
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock StartupConfiguration startupConfiguration;
    @Mock Listener listener;
    @InjectMocks
    Meta meta;

    @Before
    public void beforeEach() throws URISyntaxException {
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        when(startupConfiguration.getDatabasePath()).thenReturn(path);
    }

    @Test
    public void doNotRunForeverInTest() {
        meta.run(System.out, listener, new CommandLineProvider("--test"));
    }

    @Test
    public void loadStartupConfiguration() {
        meta.run(System.out, listener, new CommandLineProvider("--test"));
        verify(startupConfiguration).load(ConfigurationDefaults.CONFIGURATION_PATH);
    }

    @Test
    public void handleLoadException()
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        doThrow(new StartupConfigurationNotFound("")).when(startupConfiguration).load(ConfigurationDefaults.CONFIGURATION_PATH);
        meta.run(new PrintStream(output), listener, new CommandLineProvider("--test"));
        String expected = "CRITICAL: Configuration file '" + ConfigurationDefaults.CONFIGURATION_PATH + "' not found." + System.lineSeparator();
        assertThat(output.toString(), equalTo(expected));
    }
}
