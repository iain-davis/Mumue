package org.ruhlendavis.mumue.configuration.commandline;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandLineProviderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void supportTestOption() {
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(CommandLineOptionName.TEST));
        CommandLine commandLine = commandLineProvider.get();

        assertTrue(commandLine.hasOption(CommandLineOptionName.TEST));
    }

    @Test
    public void supportSOption() {
        String path = RandomStringUtils.randomAlphabetic(13);
        CommandLineProvider commandLineProvider = new CommandLineProvider("-s", path);

        CommandLine commandLine = commandLineProvider.get();

        assertThat(commandLine.getOptionValue(CommandLineOptionName.STARTUP_CONFIGURATION_PATH), equalTo(path));
    }

    @Test
    public void supportStartupConfigurationOption() {
        String path = RandomStringUtils.randomAlphabetic(13);
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(CommandLineOptionName.STARTUP_CONFIGURATION_PATH), path);

        CommandLine commandLine = commandLineProvider.get();

        assertThat(commandLine.getOptionValue(CommandLineOptionName.STARTUP_CONFIGURATION_PATH), equalTo(path));
    }

    @Test
    public void startupConfigurationOptionRequiresArgument() {
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(CommandLineOptionName.STARTUP_CONFIGURATION_PATH));

        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(MissingArgumentException.class));

        commandLineProvider.get();
    }

    private String getSwitch(String option) {
        return "--" + option;
    }
}
