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

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;
import org.ruhlendavis.meta.configuration.startup.FileFactory;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationAnalyzer;
import org.ruhlendavis.meta.datastore.DataStore;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MetaMainTest {
    @Mock private StartupConfiguration startupConfiguration;
    @Mock private StartupConfigurationAnalyzer startupConfigurationAnalyzer;
    @Mock private DataStore dataStore;
    @Mock private File file;
    @Mock private FileFactory fileFactory;
    @Mock private Listener listener;
    @Mock private Thread thread;

    @InjectMocks MetaMain main;

    @Before
    public void beforeEach() {
        when(listener.isRunning()).thenReturn(false);
        when(fileFactory.create(anyString())).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(file.isDirectory()).thenReturn(false);
        doNothing().when(startupConfiguration).load(anyString());
        when(startupConfigurationAnalyzer.isValid(startupConfiguration)).thenReturn(true);
    }


    @Test
    public void runHandlesPrintsErrorForUnknownPath() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.create(path)).thenReturn(file);
        when(file.exists()).thenReturn(false);
        main.run(new PrintStream(output), new String[]{path});
        assertEquals("CRITICAL: Configuration file '" + path + "' not found." + System.lineSeparator(), output.toString());
    }

    @Test
    public void runSetsMainListenerPort() {
        int port = 9999;
        when(startupConfiguration.getTelnetPort()).thenReturn(port);
        main.run(System.out, new String[]{});
        verify(listener).setPort(port);
    }

    @Test
    public void runWithInvalidConfigurationQuits() {
        when(startupConfigurationAnalyzer.isValid(startupConfiguration)).thenReturn(false);
        main.run(System.out, new String[]{});
        verify(thread, never()).start();
    }

    @Test
    public void runWithInvalidConfigurationDisplaysError() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        when(startupConfigurationAnalyzer.isValid(startupConfiguration)).thenReturn(false);
        main.run(new PrintStream(output), new String[]{});
        assertEquals("CRITICAL: Configuration file '" + ConfigurationDefaults.CONFIGURATION_PATH + "' is invalid." + System.lineSeparator(), output.toString());
    }

    @Test
    public void runRunsMainListener() {
        main.run(System.out, new String[]{});
        verify(thread).start();
    }

    @Test
    public void runPopulatesDataStoreWhenEmpty() {
//        when(dataStore.isDatabaseEmpty(fileConfiguration)).thenReturn(true);
//        main.run(System.out, new String[]{});
//        verify(dataStore).populateDatabase(fileConfiguration);
    }
}
