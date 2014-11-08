package org.ruhlendavis.meta.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceFactoryTest {
    @Mock Configuration configuration;
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @Test
    public void createDataSourceReturnsDataSource() {
        assertNotNull(dataSourceFactory.createDataSource(configuration));
    }

    @Test
    public void createDataSourceSetsDriver() {
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(configuration);
        assertEquals("org.h2.Driver", source.getDriverClassName());
    }

    @Test
    public void createDataSourceSetsUsername() {
        String username = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabaseUsername()).thenReturn(username);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(configuration);
        assertEquals(username, source.getUsername());
    }

    @Test
    public void createDataSourceSetsPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabasePassword()).thenReturn(password);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(configuration);
        assertEquals(password, source.getPassword());
    }

    @Test
    public void createDataSourceSetsUrl() {
        String path = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabasePath()).thenReturn(path);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(configuration);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        assertEquals(expected, source.getUrl());
    }
}
