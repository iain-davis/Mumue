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

import org.ruhlendavis.meta.configuration.file.FileConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceFactoryTest {
    @Mock
    FileConfiguration fileConfiguration;
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @Test
    public void createDataSourceReturnsDataSource() {
        assertNotNull(dataSourceFactory.createDataSource(fileConfiguration));
    }

    @Test
    public void createDataSourceSetsDriver() {
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(fileConfiguration);
        assertEquals("org.h2.Driver", source.getDriverClassName());
    }

    @Test
    public void createDataSourceSetsUsername() {
        String username = RandomStringUtils.randomAlphabetic(13);
        when(fileConfiguration.getDatabaseUsername()).thenReturn(username);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(fileConfiguration);
        assertEquals(username, source.getUsername());
    }

    @Test
    public void createDataSourceSetsPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        when(fileConfiguration.getDatabasePassword()).thenReturn(password);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(fileConfiguration);
        assertEquals(password, source.getPassword());
    }

    @Test
    public void createDataSourceSetsUrl() {
        String path = RandomStringUtils.randomAlphabetic(13);
        when(fileConfiguration.getDatabasePath()).thenReturn(path);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.createDataSource(fileConfiguration);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        assertEquals(expected, source.getUrl());
    }
}
