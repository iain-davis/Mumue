package org.mumue.mumue.configuration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.commandline.CommandLineConfiguration;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ConfigurationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();

    @Mock CommandLineConfiguration commandLineConfiguration;
    @Mock OnlineConfiguration onlineConfiguration;
    @Mock ComponentIdManager componentIdManager;
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

    @Test
    public void getTelnetPort() {
        int port = RandomUtils.nextInt(1024, 65536);
        when(onlineConfiguration.getTelnetPort()).thenReturn(port);
        assertThat(configuration.getTelnetPort(), equalTo(port));
    }

    @Test
    public void getServerLocale() {
        String serverLocale = RandomStringUtils.randomAlphabetic(5);
        when(onlineConfiguration.getServerLocale()).thenReturn(serverLocale);
        assertThat(configuration.getServerLocale(), equalTo(serverLocale));
    }

    @Test
    public void getNewComponentId() {
        long id = RandomUtils.nextLong(100, 200);
        when(componentIdManager.getNewComponentId(onlineConfiguration)).thenReturn(id);
        assertThat(configuration.getNewComponentId(), equalTo(id));
    }
}
