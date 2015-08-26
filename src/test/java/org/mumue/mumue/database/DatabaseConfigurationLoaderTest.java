package org.mumue.mumue.database;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DatabaseConfigurationLoaderTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final DatabaseConfigurationLoader loader = new DatabaseConfigurationLoader();

    @Test
    public void returnEmptyPropertiesWithNoPath() {
        DatabaseConfiguration configuration = loader.load();

        assertThat(configuration, notNullValue());
    }

    @Test
    public void returnEmptyPropertiesWithEmptyFile() throws IOException {
        String path = temporaryFolder.newFile().getAbsolutePath();
        DatabaseConfiguration configuration = loader.load(path);

        assertThat(configuration, notNullValue());
    }

    @Test
    public void throwExceptionOnFileNotFound() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(FileNotFoundException.class));

        loader.load(RandomStringUtils.randomAlphabetic(17));
    }

    @Test
    public void loadAProperty() {
        String url = RandomStringUtils.randomAlphabetic(17);
        String path = setupFile(DatabaseConfiguration.DATABASE_URL, url);

        DatabaseConfiguration configuration = loader.load(path);

        assertThat(configuration.getUrl(), equalTo(url));
    }

    private String setupFile(String key, String value) {
        try {
            File file = temporaryFolder.newFile();
            FileWriter writer = new FileWriter(file);
            writer.write(key + "=" + value + "\n");
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}