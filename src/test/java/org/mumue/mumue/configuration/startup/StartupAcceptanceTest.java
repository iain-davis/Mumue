package org.mumue.mumue.configuration.startup;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.acceptance.MumueRunnable;
import org.mumue.mumue.acceptance.MumueRunner;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.configuration.commandline.CommandLineOptionName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StartupAcceptanceTest {
    public static final String WELCOME_TO_MUMUE = "Welcome to Mumue!";
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static final MumueRunner mumueRunner = new MumueRunner();

    @After
    public void afterEach() {
        mumueRunner.stop();
    }

    @After
    public void afterClass() {
        mumueRunner.stop();
    }

    @Test
    public void useDefaultTelnetPort() {
        MumueRunnable mumue = new MumueRunnable();
        mumueRunner.runMumue(mumue, ConfigurationDefaults.TELNET_PORT);

        assertThat(mumueRunner.getTelnetOutput(), containsString("Welcome to Mumue!"));
        //noinspection ResultOfMethodCallIgnored
        new File(ConfigurationDefaults.DATABASE_PATH).delete();
    }

    @Test
    public void useProvidedConfigurationFile() {
        Integer port = new Random().nextInt(1000) + 1024;
        Properties properties = defaultProperties();
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, port.toString());
        String configurationFile = setupConfigurationFile(properties);

        MumueRunnable mumue = new MumueRunnable("--" + CommandLineOptionName.STARTUP_CONFIGURATION_PATH, configurationFile);
        mumueRunner.runMumue(mumue, port);

        assertThat(mumueRunner.getTelnetOutput(), containsString(WELCOME_TO_MUMUE));
    }

    @Test
    public void useDatabaseUrlWhenProvided() throws Exception {
        String fileName = RandomStringUtils.randomAlphabetic(17);
        String url = "jdbc:h2:./" + fileName + ";MV_STORE=FALSE;MVCC=FALSE";
        File file = temporaryFolder.newFile("./" + fileName + ".h2.db");

        Properties properties = defaultProperties();
        properties.setProperty(StartupConfigurationOptionName.DATABASE_URL, url);

        String configurationFile = setupConfigurationFile(properties);

        MumueRunnable mumue = new MumueRunnable("--" + CommandLineOptionName.STARTUP_CONFIGURATION_PATH, configurationFile);
        mumueRunner.runMumue(mumue, ConfigurationDefaults.TELNET_PORT);

        String expected = "Database Url: jdbc:h2:./" + fileName + ";MV_STORE=FALSE;MVCC=FALSE";
        assertThat(mumueRunner.getTelnetOutput(), containsString(WELCOME_TO_MUMUE));
        assertThat(mumueRunner.getConsoleOutput(), containsString(expected));
        assertTrue(file.exists());
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

    private Properties defaultProperties() {
        try {
            Properties properties = new Properties();
            File database = temporaryFolder.newFile("acceptance.h2.db");
            properties.setProperty(StartupConfigurationOptionName.DATABASE_PATH, database.getAbsolutePath());
            properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, String.valueOf(ConfigurationDefaults.TELNET_PORT));
            properties.setProperty(StartupConfigurationOptionName.DATABASE_PASSWORD, ConfigurationDefaults.DATABASE_PASSWORD);
            properties.setProperty(StartupConfigurationOptionName.DATABASE_USERNAME, ConfigurationDefaults.DATABASE_USERNAME);
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
