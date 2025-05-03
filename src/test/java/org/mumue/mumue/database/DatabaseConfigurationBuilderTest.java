package org.mumue.mumue.database;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DatabaseConfigurationBuilderTest {
    private final DatabaseConfigurationBuilder builder = new DatabaseConfigurationBuilder();

    @Test
    public void neverReturnNull() {
        assertThat(builder.build(), notNullValue());
    }

    @Test
    public void setProperty() {
        String value = RandomStringUtils.insecure().nextAlphabetic(17);
        DatabaseConfiguration configuration = builder.with(DatabaseConfiguration.DATABASE_DRIVER_NAME, value).build();

        assertThat(configuration.getDriverName(), equalTo(value));
    }
}
