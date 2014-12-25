package org.ruhlendavis.meta;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import javax.sql.DataSource;

import com.google.common.io.Resources;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;
import org.ruhlendavis.meta.acceptance.TestConstants;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationProvider;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock StartupConfiguration startupConfiguration;
    @Mock DataSourceFactory dataSourceFactory;
    @Mock QueryRunnerProvider queryRunnerProvider;
    @Mock DatabaseInitializer databaseInitializer;
    @Mock ConfigurationProvider configurationProvider;
    @Mock Configuration configuration;
    @Mock Listener listener;
    @InjectMocks Main main;

    private DataSource dataSource = DatabaseHelper.setupDataSource();

    @Before
    public void beforeEach() throws URISyntaxException {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithoutSchema();
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
        when(dataSourceFactory.create(startupConfiguration)).thenReturn(dataSource);
        when(queryRunnerProvider.create(dataSource)).thenReturn(queryRunner);
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        when(startupConfiguration.getDatabasePath()).thenReturn(path);
        when(configurationProvider.create(any(CommandLineConfiguration.class), eq(startupConfiguration), any(OnlineConfiguration.class))).thenCallRealMethod();
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

    @Test
    public void createQueryRunnerFromDataSource() {
        main.run("--test");
        verify(queryRunnerProvider).create(dataSource);
    }

    @Test
    public void createConfigurationFromConfigurations() {
        main.run("--test");
        verify(configurationProvider).create(any(CommandLineConfiguration.class), eq(startupConfiguration), any(OnlineConfiguration.class));
    }

    @Test
    public void initializeDatabase() {
        main.run("--test");
        verify(databaseInitializer).initialize();
    }
}
