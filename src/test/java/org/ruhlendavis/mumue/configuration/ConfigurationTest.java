package org.ruhlendavis.mumue.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationTest {
    @Mock CommandLineConfiguration commandLineConfiguration;
    @Mock OnlineConfiguration onlineConfiguration;
    @Mock StartupConfiguration startupConfiguration;
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
        when(startupConfiguration.getTelnetPort()).thenReturn(port);
        assertThat(configuration.getTelnetPort(), equalTo(port));
    }

    @Test
    public void getServerLocale() {
        String serverLocale = RandomStringUtils.randomAlphabetic(5);
        when(onlineConfiguration.getServerLocale()).thenReturn(serverLocale);
        assertThat(configuration.getServerLocale(), equalTo(serverLocale));
    }

    @Test
    public void getLastComponentId() {
        long lastComponentId = RandomUtils.nextLong(100, 200);
        when(onlineConfiguration.getNextComponentId()).thenReturn(lastComponentId);
        assertThat(configuration.getNextComponentId(), equalTo(lastComponentId));
    }

    @Test
    public void getDatabaseUsername() {
        String databaseUsername = RandomStringUtils.randomAlphabetic(5);
        when(startupConfiguration.getDatabaseUsername()).thenReturn(databaseUsername);
        assertThat(configuration.getDatabaseUsername(), equalTo(databaseUsername));
    }

    @Test
    public void getDatabasePassword() {
        String databasePassword = RandomStringUtils.randomAlphabetic(5);
        when(startupConfiguration.getDatabasePassword()).thenReturn(databasePassword);
        assertThat(configuration.getDatabasePassword(), equalTo(databasePassword));
    }

    @Test
    public void getDatabasePath() {
        String databasePath = RandomStringUtils.randomAlphabetic(5);
        when(startupConfiguration.getDatabasePath()).thenReturn(databasePath);
        assertThat(configuration.getDatabasePath(), equalTo(databasePath));
    }
}
