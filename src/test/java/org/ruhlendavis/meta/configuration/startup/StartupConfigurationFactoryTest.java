package org.ruhlendavis.meta.configuration.startup;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

@RunWith(MockitoJUnitRunner.class)
public class StartupConfigurationFactoryTest {
    private final String path = RandomStringUtils.randomAlphabetic(13);
    @Mock File file;
    @Mock Properties properties;
    @Mock InputStream input;
    @Mock OutputStream output;
    @Mock FileFactory fileFactory;
    @Mock
    FileInputStreamFactory fileInputStreamFactory;
    @Mock OutputStreamFactory outputStreamFactory;
    @InjectMocks StartupConfigurationFactory startupConfigurationFactory;

    @Before
    public void beforeEach() {
        when(fileFactory.create(path)).thenReturn(file);
        when(properties.getProperty(anyString())).thenCallRealMethod();
        when(properties.setProperty(anyString(), anyString())).thenCallRealMethod();
    }

    @Test
    public void createReturnsStartupConfiguration() throws IOException {
        when(fileInputStreamFactory.create(path)).thenReturn(input);
        doNothing().when(properties).load(input);
        when(file.isFile()).thenReturn(true);

        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(path);

        assertNotNull(startupConfiguration);
        assertThat(startupConfiguration, is(instanceOf(StartupConfiguration.class)));
    }

    @Test
    public void createLoadsConfiguration() throws IOException {
        when(fileInputStreamFactory.create(path)).thenReturn(input);
        doNothing().when(properties).load(input);
        when(file.isFile()).thenReturn(true);

        startupConfigurationFactory.create(path);

        verify(properties).load(input);
    }

    @Test
    public void createSavesConfigurationWhenFileDoesNotExist() throws URISyntaxException, IOException {
        when(outputStreamFactory.create(anyString())).thenReturn(output);
        doNothing().when(properties).store(output, path);

        startupConfigurationFactory.create(path);

        verify(outputStreamFactory).create(path);
        verify(properties).store(output, "");
    }

    @Test
    public void createSetsDefaultPropertiesWhenFileDoesNotExist() throws URISyntaxException, IOException {
        when(outputStreamFactory.create(anyString())).thenReturn(output);
        doNothing().when(properties).store(output, path);

        startupConfigurationFactory.create(path);

        verify(properties).setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
        verify(properties).setProperty(StartupConfigurationOptionName.DATABASE_PATH, ConfigurationDefaults.DATABASE_PATH);
        verify(properties).setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
        verify(properties).setProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
    }
}
