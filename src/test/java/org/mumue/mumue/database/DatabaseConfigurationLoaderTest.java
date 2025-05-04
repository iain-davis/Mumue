package org.mumue.mumue.database;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseConfigurationLoaderTest {
    private final DatabaseConfigurationLoader loader = new DatabaseConfigurationLoader();

    @Test
    void returnEmptyPropertiesWithNoPath() {
        DatabaseConfiguration configuration = loader.load();

        assertThat(configuration, notNullValue());
    }

    @Test
    void returnEmptyPropertiesWithEmptyFile(@TempDir Path temporaryFolder) throws IOException {
        Path temporaryFile = temporaryFolder.resolve("tempFile");
        Files.write(temporaryFile, new ArrayList<>());
        String path = temporaryFile.toAbsolutePath().toString();

        DatabaseConfiguration configuration = loader.load(path);

        assertThat(configuration, notNullValue());
    }

    @Test
    void throwExceptionOnFileNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> loader.load(RandomStringUtils.insecure().nextAlphabetic(17)));

        assertThat(exception.getCause(), instanceOf(FileNotFoundException.class));
    }

    @Test
    void loadAProperty(@TempDir Path temporaryFolder) {
        String url = RandomStringUtils.insecure().nextAlphabetic(17);
        String path = setupFile(temporaryFolder, DatabaseConfiguration.DATABASE_URL, url);

        DatabaseConfiguration configuration = loader.load(path);

        assertThat(configuration.getUrl(), equalTo(url));
    }

    @Test
    void loadTwoProperties(@TempDir Path temporaryFolder) {
        String url = RandomStringUtils.insecure().nextAlphabetic(17);
        String dbPath = RandomStringUtils.insecure().nextAlphabetic(17);
        String path = setupFile(temporaryFolder, DatabaseConfiguration.DATABASE_URL, url, DatabaseConfiguration.DATABASE_PATH, dbPath);

        DatabaseConfiguration configuration = loader.load(path);

        assertThat(configuration.getUrl(), equalTo(url));
        assertThat(configuration.getPath(), equalTo(dbPath));
    }

    private String setupFile(Path temporaryFolder, String key, String value, String... additional) {
        try {
            Path temporaryFile = temporaryFolder.resolve("tempFile");
            List<String> lines = new ArrayList<>();
            lines.add(key + "=" + value + "\n");
            if (additional.length == 2) {
                lines.add(additional[0] + "=" + additional[1]);
            }
            Files.write(temporaryFile, lines);
            return temporaryFile.toString();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
