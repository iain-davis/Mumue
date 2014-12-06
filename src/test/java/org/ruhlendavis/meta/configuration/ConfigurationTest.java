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

import org.ruhlendavis.meta.GlobalConstants;

public class ConfigurationTest {
    @Test
    public void isTestReturnsTrue() {
        CommandLine commandLine = setupCommandLine("test", "");
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
        assertThat(configuration.getPort(), equalTo(GlobalConstants.DEFAULT_TELNET_PORT));
    }

    @Test
    public void getPortReturnsPort() {
        Integer port = RandomUtils.nextInt(1024, 65536);
        CommandLine commandLine = setupCommandLine("port", port.toString());
        Configuration configuration = new Configuration(commandLine);
        assertThat(configuration.getPort(), equalTo(port));
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
