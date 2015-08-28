package org.mumue.mumue.database;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.MumueRunner;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DatabaseConfigurationAcceptanceTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void whenNoDatabaseConfigurationFileProvidedUseDefaultPath() {
        File file = FileUtils.getFile(DatabaseConfiguration.DEFAULT_PATH + ".h2.db");

        MumueRunner runner = new MumueRunner();
        runner.cleanupDatabase();

        assertThat(file.exists(), is(false));

        runner.run(ConfigurationDefaults.TELNET_PORT);
        runner.stopAfterTelnet();

        assertThat(file.exists(), is(true));

        runner.cleanupDatabase();
    }

    @Ignore
    @Test
    public void useDatabaseUrlWhenProvided() throws Exception {
        String url = DatabaseHelper.MEMORY_DATABASE;
        String expected = "Database Url: " + url;

        Properties properties = defaultProperties();
        properties.setProperty(DatabaseConfiguration.DATABASE_URL, url);

        String configurationFile = setupConfigurationFile(properties);

        MumueRunner runner = new MumueRunner();
        runner.run(ConfigurationDefaults.TELNET_PORT, configurationFile);
        runner.stopAfterTelnet();

        assertThat(runner.getConsoleOutput(), containsString(expected));
    }

    private Properties defaultProperties() {
        try {
            Properties properties = new Properties();
            File database = temporaryFolder.newFile("database-configuration-acceptance.h2.db");
            properties.setProperty(DatabaseConfiguration.DEFAULT_PATH, database.getAbsolutePath());
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String setupConfigurationFile(Properties properties) {
        try {
            File file = temporaryFolder.newFile();
            FileWriter writer = new FileWriter(file);
            properties.store(writer, "");
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
