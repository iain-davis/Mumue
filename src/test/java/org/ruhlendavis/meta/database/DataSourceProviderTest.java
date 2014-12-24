package org.ruhlendavis.meta.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceProviderTest {
    @Mock StartupConfiguration startupConfiguration;
    @InjectMocks DataSourceProvider dataSourceProvider;

    @Test
    public void createDataSourceReturnsDataSource() {
        DataSource source = dataSourceProvider.get();
        assertNotNull(source);
        assertThat(source, is(instanceOf(DataSource.class)));
    }

    @Test
    public void createDataSourceSetsDriver() {
        BasicDataSource source = (BasicDataSource) dataSourceProvider.get();
        assertEquals("org.h2.Driver", source.getDriverClassName());
    }

    @Test
    public void createDataSourceSetsUsername() {
        String username = RandomStringUtils.randomAlphabetic(13);
        when(startupConfiguration.getDatabaseUsername()).thenReturn(username);
        BasicDataSource source = (BasicDataSource) dataSourceProvider.get();
        assertEquals(username, source.getUsername());
    }

    @Test
    public void createDataSourceSetsPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        when(startupConfiguration.getDatabasePassword()).thenReturn(password);
        BasicDataSource source = (BasicDataSource) dataSourceProvider.get();
        assertEquals(password, source.getPassword());
    }

    @Test
    public void createDataSourceSetsUrl() {
        String path = RandomStringUtils.randomAlphabetic(13);
        when(startupConfiguration.getDatabasePath()).thenReturn(path);
        BasicDataSource source = (BasicDataSource) dataSourceProvider.get();
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        assertEquals(expected, source.getUrl());
    }
}
