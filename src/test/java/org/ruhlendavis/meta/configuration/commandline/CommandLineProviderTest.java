package org.ruhlendavis.meta.configuration.commandline;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandLineProviderTest {
    @Rule public ExpectedException thrown= ExpectedException.none();

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
        thrown.expect(RuntimeException.class);
        thrown.expectCause(new CauseMatcher(MissingArgumentException.class));
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(CommandLineOptionName.STARTUP_CONFIGURATION_PATH));
        commandLineProvider.get();
    }

    private String getSwitch(String option) {
        return "--" + option;
    }

    private static class CauseMatcher extends TypeSafeMatcher<Throwable> {
        private final Class<? extends Throwable> type;

        public CauseMatcher(Class<? extends Throwable> type) {
            this.type = type;
        }

        @Override
        protected boolean matchesSafely(Throwable item) {
            return item.getClass().isAssignableFrom(type);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("expects type ").appendValue(type);
        }
    }
}
