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

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceFactoryTest {
    @Mock Configuration configuration;
    @InjectMocks DataSourceFactory dataSourceFactory;

    @Test
    public void createDataSourceReturnsDataSource() {
        DataSource source = dataSourceFactory.create(configuration);
        assertNotNull(source);
        assertThat(source, is(instanceOf(DataSource.class)));
    }

    @Test
    public void createDataSourceSetsDriver() {
        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);
        assertEquals("org.h2.Driver", source.getDriverClassName());
    }

    @Test
    public void createDataSourceSetsUsername() {
        String username = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabaseUsername()).thenReturn(username);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);
        assertEquals(username, source.getUsername());
    }

    @Test
    public void createDataSourceSetsPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabasePassword()).thenReturn(password);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);
        assertEquals(password, source.getPassword());
    }

    @Test
    public void createDataSourceSetsUrl() {
        String path = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabasePath()).thenReturn(path);
        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        assertEquals(expected, source.getUrl());
    }
}
