package org.mumue.mumue.database;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mumue.mumue.MumueRunner;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DatabaseConfigurationAcceptanceTest {
    @Test
    public void whenNoDatabaseConfigurationFileProvidedUseDefaultPath() {
        cleanupDatabase();
        MumueRunner runner = new MumueRunner();
        File file = FileUtils.getFile(DatabaseConfiguration.DEFAULT_PATH + ".h2.db");
        runner.run(ConfigurationDefaults.TELNET_PORT);
        runner.stopAfterTelnet();
        assertThat(file.exists(), is(true));

        cleanupDatabase();
    }

    private void cleanupDatabase() {
        try {
            FileUtils.forceDelete(FileUtils.getFile(DatabaseConfiguration.DEFAULT_PATH + ".h2.db"));
        } catch (FileNotFoundException ignored) {
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
