package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {
    @Mock CommandLineConfiguration commandLineConfiguration;
    @InjectMocks Configuration configuration;

    @Test
    public void isTestReturnsTrue() {
        when(commandLineConfiguration.isTest()).thenReturn(true);
        assertTrue(configuration.isTest());
    }

    @Test
    public void isTestReturnsFalse() {
        when(commandLineConfiguration.isTest()).thenReturn(false);
        assertFalse(configuration.isTest());
    }
}
