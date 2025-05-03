package org.mumue.mumue.database;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DatabaseConfigurationTest {
    private static final String JDBC_URL_SUFFIX = ";MV_STORE=FALSE;MVCC=FALSE;NON_KEYWORDS=value";
    public static final String JDBC_URL_PREFIX = "jdbc:h2:";

    @Test
    public void returnsDefaultDriverName() {
        DatabaseConfiguration configuration = new DatabaseConfiguration(new Properties());

        assertThat(configuration.getDriverName(), equalTo(DatabaseConfiguration.DEFAULT_DRIVER_CLASS_NAME));
    }

    @Test
    public void returnsGivenDriverName() {
        String driver = RandomStringUtils.insecure().nextAlphabetic(17);

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_DRIVER_NAME, driver);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getDriverName(), equalTo(driver));
    }

    @Test
    public void returnsDefaultPath() {
        DatabaseConfiguration configuration = new DatabaseConfiguration(new Properties());

        assertThat(configuration.getPath(), equalTo(DatabaseConfiguration.DEFAULT_PATH));
    }

    @Test
    public void returnsGivenPath() {
        String path = RandomStringUtils.insecure().nextAlphabetic(17);

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_PATH, path);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getPath(), equalTo(path));
    }

    @Test
    public void returnsDefaultUsername() {
        DatabaseConfiguration configuration = new DatabaseConfiguration(new Properties());

        assertThat(configuration.getUsername(), equalTo(DatabaseConfiguration.DEFAULT_USERNAME));
    }

    @Test
    public void returnsGivenUsername() {
        String username = RandomStringUtils.insecure().nextAlphabetic(17);

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_USERNAME, username);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUsername(), equalTo(username));
    }

    @Test
    public void returnsDefaultPassword() {
        DatabaseConfiguration configuration = new DatabaseConfiguration(new Properties());

        assertThat(configuration.getPassword(), equalTo(DatabaseConfiguration.DEFAULT_PASSWORD));
    }

    @Test
    public void returnsGivenPassword() {
        String password = RandomStringUtils.insecure().nextAlphabetic(17);

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_PASSWORD, password);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getPassword(), equalTo(password));
    }

    @Test
    public void whenUrlIsNullReturnsH2PathBasedUrl() {
        String path = RandomStringUtils.insecure().nextAlphabetic(17);
        String expected = JDBC_URL_PREFIX + path + JDBC_URL_SUFFIX;

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_PATH, path);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUrl(), equalTo(expected));
    }

    @Test
    public void whenUrlIsNullAndPathIsNullReturnsH2DefaultPathBasedUrl() {
        String expected = JDBC_URL_PREFIX + DatabaseConfiguration.DEFAULT_PATH + JDBC_URL_SUFFIX;

        Properties properties = new Properties();

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUrl(), equalTo(expected));
    }

    @Test
    public void whenUrlIsEmptyReturnsH2PathBasedUrl() {
        String path = RandomStringUtils.insecure().nextAlphabetic(17);
        String expected = JDBC_URL_PREFIX + path + JDBC_URL_SUFFIX;

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_PATH, path);
        properties.setProperty(DatabaseConfiguration.DATABASE_URL, "");

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUrl(), equalTo(expected));
    }

    @Test
    public void whenUrlIsBlankReturnsH2PathBasedUrl() {
        String path = RandomStringUtils.insecure().nextAlphabetic(17);
        String expected = JDBC_URL_PREFIX + path + JDBC_URL_SUFFIX;

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_PATH, path);
        properties.setProperty(DatabaseConfiguration.DATABASE_URL, "    \t");

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUrl(), equalTo(expected));
    }

    @Test
    public void returnsGivenUrl() {
        String url = RandomStringUtils.insecure().nextAlphabetic(17);

        Properties properties = new Properties();
        properties.setProperty(DatabaseConfiguration.DATABASE_URL, url);

        DatabaseConfiguration configuration = new DatabaseConfiguration(properties);

        assertThat(configuration.getUrl(), equalTo(url));
    }
}
