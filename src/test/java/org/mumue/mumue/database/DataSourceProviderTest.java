package org.mumue.mumue.database;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DataSourceProviderTest {
    private final DatabaseConfiguration configuration = new DatabaseConfigurationBuilder()
            .with(DatabaseConfiguration.DATABASE_DRIVER_NAME, RandomStringUtils.insecure().nextAlphabetic(13))
            .with(DatabaseConfiguration.DATABASE_PASSWORD, RandomStringUtils.insecure().nextAlphabetic(17))
            .with(DatabaseConfiguration.DATABASE_URL, RandomStringUtils.insecure().nextAlphabetic(14))
            .with(DatabaseConfiguration.DATABASE_USERNAME, RandomStringUtils.insecure().nextAlphabetic(16))
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
