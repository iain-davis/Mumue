package org.ruhlendavis.meta;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import com.google.common.io.Resources;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;
import org.ruhlendavis.meta.acceptance.TestConstants;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock StartupConfiguration startupConfiguration;
    @Mock DataSourceFactory dataSourceFactory;
    @Mock Listener listener;
    @InjectMocks
    Main main;

    @Before
    public void beforeEach() throws URISyntaxException {
        DataSource dataSource = DatabaseHelper.setupDataSource();
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
        when(dataSourceFactory.create(startupConfiguration)).thenReturn(dataSource);
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        when(startupConfiguration.getDatabasePath()).thenReturn(path);
    }

    @Test
    public void doNotRunForeverInTest() {
        main.run("--test");
    }

    @Test
    public void useStartupConfigurationPathShortSpecifiedOnCommandLine() {
        String path = RandomStringUtils.randomAlphabetic(13);
        main.run("-s", path, "--test");
        verify(startupConfigurationFactory).create(path);
    }

    @Test
    public void useStartupConfigurationPathLongSpecifiedOnCommandLine() {
        String path = RandomStringUtils.randomAlphabetic(13);
        main.run("--startupConfiguration", path, "--test");
        verify(startupConfigurationFactory).create(path);
    }

    @Test
    public void createDataSourceFromStartupConfiguration() {
        main.run("--test");
        verify(dataSourceFactory).create(startupConfiguration);
    }
}
