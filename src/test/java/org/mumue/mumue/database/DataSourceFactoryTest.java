package org.mumue.mumue.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mumue.mumue.configuration.Configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceFactoryTest {
    @Mock Configuration configuration;
    @InjectMocks DataSourceFactory dataSourceFactory;

    @Test
    public void createDataSourceSetsDriver() {
        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);
        assertThat(source.getDriverClassName(), equalTo("org.h2.Driver"));
    }

    @Test
    public void createDataSourceSetsUsername() {
        String username = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabaseUsername()).thenReturn(username);

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getUsername(), equalTo(username));
    }

    @Test
    public void createDataSourceSetsPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabasePassword()).thenReturn(password);

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getPassword(), equalTo(password));
    }

    @Test
    public void createDataSourceSetsH2UrlWhenUrlIsNull() {
        String path = RandomStringUtils.randomAlphabetic(13);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        when(configuration.getDatabasePath()).thenReturn(path);
        when(configuration.getDatabaseUrl()).thenReturn(null);

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getUrl(), equalTo(expected));
    }

    @Test
    public void createDataSourceSetsH2UrlWhenUrlIsEmpty() {
        String path = RandomStringUtils.randomAlphabetic(13);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        when(configuration.getDatabasePath()).thenReturn(path);
        when(configuration.getDatabaseUrl()).thenReturn("");

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getUrl(), equalTo(expected));
    }

    @Test
    public void createDataSourceSetsH2UrlWhenUrlIsBlank() {
        String path = RandomStringUtils.randomAlphabetic(13);
        String expected = "jdbc:h2:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
        when(configuration.getDatabasePath()).thenReturn(path);
        when(configuration.getDatabaseUrl()).thenReturn("    \t");

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getUrl(), equalTo(expected));
    }

    @Test
    public void createDataSourceSetsUrlIfProvided() {
        String url = RandomStringUtils.randomAlphabetic(13);
        when(configuration.getDatabaseUrl()).thenReturn(url);

        BasicDataSource source = (BasicDataSource) dataSourceFactory.create(configuration);

        assertThat(source.getUrl(), equalTo(url));
    }
}
