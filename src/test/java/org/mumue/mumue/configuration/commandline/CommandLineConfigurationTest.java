package org.mumue.mumue.configuration.commandline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import org.mumue.mumue.configuration.ConfigurationDefaults;

public class CommandLineConfigurationTest {
    @Test
    public void isTestReturnsTrue() {
        CommandLine commandLine = setupCommandLine(CommandLineOptionName.TEST, "");
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLine);
        assertTrue(commandLineConfiguration.isTest());
    }

    @Test
    public void isTestReturnsFalse() {
        CommandLine commandLine = setupCommandLine("anything", "");
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLine);
        assertFalse(commandLineConfiguration.isTest());
    }

    @Test
    public void startupConfigurationPathDefaults() {
        CommandLine commandLine = setupCommandLine("anything", "");
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLine);
        assertEquals(ConfigurationDefaults.CONFIGURATION_PATH, commandLineConfiguration.getStartupConfigurationPath());
    }

    @Test
    public void startupConfigurationPath() {
        String path = RandomStringUtils.randomAlphabetic(13);
        CommandLine commandLine = setupCommandLine(CommandLineOptionName.STARTUP_CONFIGURATION_PATH, path);
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLine);
        assertEquals(path, commandLineConfiguration.getStartupConfigurationPath());
    }

    private CommandLine setupCommandLine(String optionName, String optionValue) {
        boolean argumentRequired = StringUtils.isNotBlank(optionValue);
        Options options = new Options();

        if (optionName.length() == 1) {
            options.addOption(optionName, argumentRequired, "");
        } else {
            options.addOption(optionName.substring(0, 1), optionName, argumentRequired, "");
        }

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, new String[]{"--" + optionName, optionValue});
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
