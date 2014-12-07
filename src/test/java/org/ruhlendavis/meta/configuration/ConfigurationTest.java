package org.ruhlendavis.meta.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.constants.Defaults;
import org.ruhlendavis.meta.constants.OptionName;

public class ConfigurationTest {
    @Test
    public void isTestReturnsTrue() {
        CommandLine commandLine = setupCommandLine(OptionName.TEST, "");
        Configuration configuration = new Configuration(commandLine);
        assertTrue(configuration.isTest());
    }

    @Test
    public void isTestReturnsFalse() {
        CommandLine commandLine = setupCommandLine("anythin", "");
        Configuration configuration = new Configuration(commandLine);
        assertFalse(configuration.isTest());
    }

    @Test
    public void getPortReturnsDefaultPort() {
        CommandLine commandLine = setupCommandLine("anything", "");
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getPort(), equalTo(Defaults.TELNET_PORT));
    }

    @Test
    public void getPortReturnsPort() {
        Integer port = RandomUtils.nextInt(1024, 65536);
        CommandLine commandLine = setupCommandLine(OptionName.PORT, port.toString());
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getPort(), equalTo(port));
    }

    @Test
    public void getDatabaseUsernameReturnsDefault() {
        CommandLine commandLine = setupCommandLine("anything", "");
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getDatabaseUsername(), equalTo(Defaults.DATABASE_USERNAME));
    }

    @Test
    public void getDatabasePasswordReturnsDefault() {
        CommandLine commandLine = setupCommandLine("anything", "");
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getDatabasePassword(), equalTo(Defaults.DATABASE_PASSWORD));
    }

    @Test
    public void getDatabasePathReturnsDefault() {
        CommandLine commandLine = setupCommandLine("anything", "");
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getDatabasePath(), equalTo(Defaults.DATABASE_PATH));
    }

    @Test
    public void getServerLocaleReturnsDefault() {
        CommandLine commandLine = setupCommandLine("anything", "");
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getServerLocale(), equalTo(Defaults.SERVER_LOCALE));
    }

    private CommandLine setupCommandLine(String optionName, String optionValue) {
        Options options = new Options();
        if (StringUtils.isBlank(optionValue)) {
            options.addOption(optionName, false, "");
        } else {
            options.addOption(optionName, true, "");
        }
        CommandLineParser parser = new BasicParser();
        try {
            return parser.parse(options, new String[]{"--" + optionName, optionValue});
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
