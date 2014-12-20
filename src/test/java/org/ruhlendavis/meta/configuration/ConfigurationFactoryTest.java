package org.ruhlendavis.meta.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;

public class ConfigurationFactoryTest {
    private final ConfigurationFactory configurationFactory = new ConfigurationFactory();

    @Test
    public void createReturnsConfiguration() {
        assertNotNull(configurationFactory.createConfiguration(new CommandLineProvider()));
    }

    @Test
    public void useCommandLineProvider() {
        Configuration configuration = configurationFactory.createConfiguration(new CommandLineProvider("--test"));
        assertTrue(configuration.isTest());
    }
}
