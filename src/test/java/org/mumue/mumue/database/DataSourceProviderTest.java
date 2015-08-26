package org.mumue.mumue.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DataSourceProviderTest {
    private final DatabaseConfiguration configuration = new DatabaseConfigurationBuilder()
            .with(DatabaseConfiguration.DATABASE_DRIVER_NAME, RandomStringUtils.randomAlphabetic(13))
            .with(DatabaseConfiguration.DATABASE_PASSWORD, RandomStringUtils.randomAlphabetic(17))
            .with(DatabaseConfiguration.DATABASE_URL, RandomStringUtils.randomAlphabetic(14))
            .with(DatabaseConfiguration.DATABASE_USERNAME, RandomStringUtils.randomAlphabetic(16))
            .build();
    private final DataSourceProvider provider = new DataSourceProvider(configuration);

    @Test
    public void neverReturnNull() {
        assertThat(provider.get(), notNullValue());
    }

    @Test
    public void configuresDataSourceWithDriverName() {
        BasicDataSource source = (BasicDataSource) provider.get();

        assertThat(source.getDriverClassName(), equalTo(configuration.getDriverName()));
    }

    @Test
    public void configuresDataSourceWithUsername() {
        BasicDataSource source = (BasicDataSource) provider.get();

        assertThat(source.getUsername(), equalTo(configuration.getUsername()));
    }

    @Test
    public void configuresDataSourceWithPassword() {
        BasicDataSource source = (BasicDataSource) provider.get();

        assertThat(source.getPassword(), equalTo(configuration.getPassword()));
    }

    @Test
    public void configuresDataSourceWithUrl() {
        BasicDataSource source = (BasicDataSource) provider.get();

        assertThat(source.getUrl(), equalTo(configuration.getUrl()));
    }
}