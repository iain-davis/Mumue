package org.mumue.mumue.configuration.startup;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.Main;
import org.mumue.mumue.acceptance.MumueRunner;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.configuration.commandline.CommandLineOptionName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class StartupAcceptanceTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final MumueRunner mumueRunner = new MumueRunner();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    @After
    public void afterEach() {
        executorService.shutdown();
    }

    @Test
    public void useDefaultTelnetPort() {
        Runnable mumue = Main::main;
        mumueRunner.runMumue(mumue, 9998);

        assertThat(mumueRunner.getOutput(), containsString("Welcome to Mumue!"));
        //noinspection ResultOfMethodCallIgnored
        new File(ConfigurationDefaults.DATABASE_PATH).delete();
    }

    @Test
    public void useProvidedConfigurationFile() {
        Integer port = new Random().nextInt(1000) + 1024;
        Properties properties = defaultProperties();
        properties.setProperty(StartupConfigurationOptionName.TELNET_PORT, port.toString());
        String configurationFile = setupConfigurationFile(properties);

        Runnable mumue = () -> Main.main("--" + CommandLineOptionName.STARTUP_CONFIGURATION_PATH, configurationFile);
        mumueRunner.runMumue(mumue, port);

        assertThat(mumueRunner.getOutput(), containsString("Welcome to Mumue!"));
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
