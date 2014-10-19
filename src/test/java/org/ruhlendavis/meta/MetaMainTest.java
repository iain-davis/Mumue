package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationAnalyzer;
import org.ruhlendavis.meta.configuration.FileFactory;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MetaMainTest {
    @Mock
    Configuration configuration;
    @Mock
    File file;
    @Mock
    private FileFactory fileFactory;
    @Mock
    private Listener listener;
    @Mock
    private Thread thread;
    @Mock
    private ConfigurationAnalyzer configurationAnalyzer;
    @InjectMocks
    private MetaMain main = new MetaMain();

    @Before
    public void beforeEach() {
        when(listener.isRunning()).thenReturn(false);
        when(fileFactory.createFile(anyString())).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(file.isDirectory()).thenReturn(false);
        doNothing().when(configuration).load(anyString());
        when(configurationAnalyzer.isValid(configuration)).thenReturn(true);
    }

    @Test
    public void runUsesDefaultPath() throws IOException {
        main.run(new String[]{}, System.out);
        verify(fileFactory).createFile(GlobalConstants.DEFAULT_CONFIGURATION_PATH);
    }

    @Test
    public void runUsesSpecifiedPath() throws IOException, URISyntaxException {
        String path = RandomStringUtils.randomAlphabetic(15);
        main.run(new String[]{path}, System.out);
        verify(fileFactory).createFile(path);
    }

    @Test
    public void runHandlesUnknownPath() throws IOException {
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.createFile(path)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        main.run(new String[]{path}, System.out);
        verify(configuration, never()).load(path);
    }

    @Test
    public void runHandlesUnknownPathThatIsDirectory() throws IOException {
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.createFile(path)).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(file.isDirectory()).thenReturn(true);
        main.run(new String[]{path}, System.out);
        verify(configuration, never()).load(path);
    }

    @Test
    public void runWithoutSpecificConfigurationUsesDefaults() {
        when(file.exists()).thenReturn(false);
        main.run(new String[]{}, System.out);
        verify(fileFactory).createFile(GlobalConstants.DEFAULT_CONFIGURATION_PATH);
        verify(configuration, never()).load(GlobalConstants.DEFAULT_CONFIGURATION_PATH);
        verify(thread).start();
    }

    @Test
    public void runHandlesPrintsErrorForUnknownPath() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.createFile(path)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        main.run(new String[]{path}, new PrintStream(output));
        assertEquals("CRITICAL: Configuration file '" + path + "' not found." + System.lineSeparator(), output.toString());
    }

    @Test
    public void runSetsMainListenerPort() {
        int port = 9999;
        when(configuration.getPort()).thenReturn(port);
        main.run(new String[]{}, System.out);
        verify(listener).setPort(port);
    }

    @Test
    public void runWithInvalidConfigurationQuits() {
        when(configurationAnalyzer.isValid(configuration)).thenReturn(false);
        main.run(new String[]{}, System.out);
        verify(thread, never()).start();
    }

    @Test
    public void runWithInvalidConfigurationDisplaysError() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        when(configurationAnalyzer.isValid(configuration)).thenReturn(false);
        main.run(new String[]{}, new PrintStream(output));
        assertEquals("CRITICAL: Configuration file '" + GlobalConstants.DEFAULT_CONFIGURATION_PATH + "' is invalid." + System.lineSeparator(), output.toString());
    }

    @Test
    public void runRunsMainListener() {
        main.run(new String[]{}, System.out);
        verify(thread).start();
    }
}
