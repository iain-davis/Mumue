package org.ruhlendavis.meta.configuration.commandline;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.ruhlendavis.meta.constants.OptionName;

public class CommandLineProviderTest {
    @Rule public ExpectedException thrown= ExpectedException.none();

    @Test
    public void supportTestOption() {
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(OptionName.TEST));

        CommandLine commandLine = commandLineProvider.get();

        assertTrue(commandLine.hasOption(OptionName.TEST));
    }

    @Test
    public void supportPOption() {
        Integer port = RandomUtils.nextInt(1024, 65536);
        CommandLineProvider commandLineProvider = new CommandLineProvider("-p", port.toString());

        CommandLine commandLine = commandLineProvider.get();

        assertThat(commandLine.getOptionValue(OptionName.PORT), equalTo(port.toString()));
    }

    @Test
    public void supportPortOption() {
        Integer port = RandomUtils.nextInt(1024, 65536);
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(OptionName.PORT), port.toString());

        CommandLine commandLine = commandLineProvider.get();

        assertThat(commandLine.getOptionValue(OptionName.PORT), equalTo(port.toString()));
    }

    @Test
    public void portOptionRequiresArgument() {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(new CauseMatcher(MissingArgumentException.class));
        CommandLineProvider commandLineProvider = new CommandLineProvider(getSwitch(OptionName.PORT));
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
