package org.ruhlendavis.mumue.configuration.commandline;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineConfigurationFactoryTest {
    private final String[] arguments = {RandomStringUtils.randomAlphabetic(13), RandomStringUtils.randomAlphabetic(14)};
    @Mock CommandLineFactory commandLineFactory;
    @Mock CommandLine commandLine;
    @InjectMocks CommandLineConfigurationFactory commandLineConfigurationFactory = new CommandLineConfigurationFactory();

    @Test
    public void createNeverReturnsCommandLineConfiguration() {
        CommandLineConfiguration commandLineConfiguration = commandLineConfigurationFactory.create(arguments);
        assertNotNull(commandLineConfiguration);
        assertThat(commandLineConfiguration, is(instanceOf(CommandLineConfiguration.class)));
    }
    
    @Test
    public void createUsesProvidedArguments() {
        when(commandLineFactory.create(arguments)).thenReturn(commandLine);
        commandLineConfigurationFactory.create(arguments);
        verify(commandLineFactory).create(arguments);
    }
}
