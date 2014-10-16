package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ConfigurationPrompterTest {
    private Properties properties = new Properties();
    private ConfigurationPrompter prompter = new ConfigurationPrompter();
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private String inputString = RandomStringUtils.randomNumeric(4);

    @Test
    public void runPromptsForPort() {
        prompter.run(new ByteArrayInputStream(inputString.getBytes()), new PrintStream(output), properties);
        assertEquals("Enter a port for the server:" + System.lineSeparator(), output.toString());
    }

    @Test
    public void runAcceptsPortAsInput() {
        InputStream input = new ByteArrayInputStream(inputString.getBytes());
        prompter.run(input, new PrintStream(output), properties);
        assertEquals(inputString, properties.getProperty("port"));
    }

    @Test
    public void runAcceptsEnsuresPortIsGreaterThanZero() {
        String portInput = "0\n" + RandomStringUtils.randomNumeric(4);
        InputStream input = new ByteArrayInputStream(portInput.getBytes());
        prompter.run(input, new PrintStream(output), properties);
        String expected = "Enter a port for the server:" + System.lineSeparator()
                        + "Invalid port." + System.lineSeparator()
                        + "Enter a port for the server:" + System.lineSeparator();
        assertEquals(expected, output.toString());
    }
}
