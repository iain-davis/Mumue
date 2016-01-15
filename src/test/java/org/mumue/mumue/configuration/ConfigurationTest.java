package org.mumue.mumue.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

public class ConfigurationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();

    @Mock OnlineConfiguration onlineConfiguration;
    @Mock ComponentIdManager componentIdManager;
    @InjectMocks Configuration configuration;

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
