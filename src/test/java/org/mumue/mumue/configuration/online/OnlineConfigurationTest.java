package org.mumue.mumue.configuration.online;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.ConfigurationDefaults;

public class OnlineConfigurationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock OnlineConfigurationDao dao;
    @InjectMocks OnlineConfiguration onlineConfiguration;

    @Test
    public void getServerLocaleReturnsDefault() {
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE)).thenReturn("");
        assertThat(onlineConfiguration.getServerLocale(), equalTo(ConfigurationDefaults.SERVER_LOCALE));
    }

    @Test
    public void getServerLocaleReturnsServerLocale() {
        String serverLocale = RandomStringUtils.randomAlphabetic(13);
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE)).thenReturn(serverLocale);
        assertThat(onlineConfiguration.getServerLocale(), equalTo(serverLocale));
    }

    @Test
    public void getLastComponentIdReturnsDefault() {
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.LAST_COMPONENT_ID)).thenReturn("");
        assertThat(onlineConfiguration.getLastComponentId(), equalTo(ConfigurationDefaults.LAST_COMPONENT_ID));

    }

    @Test
    public void getLastComponentIdReturnsLastComponentId() {
        Long lastComponentId = RandomUtils.nextLong(100, 200);
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.LAST_COMPONENT_ID)).thenReturn(String.valueOf(lastComponentId));
        assertThat(onlineConfiguration.getLastComponentId(), equalTo(lastComponentId));
    }

    @Test
    public void getTelnetPortReturnsDefaultIfEmpty() {
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.TELNET_PORT)).thenReturn("");
        assertThat(onlineConfiguration.getTelnetPort(), equalTo(ConfigurationDefaults.TELNET_PORT));
    }

    @Test
    public void getTelnetPortReturnsTelnetPort() {
        int port = new Random().nextInt(1000) + 1024;
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.TELNET_PORT)).thenReturn(String.valueOf(port));
        assertThat(onlineConfiguration.getTelnetPort(), equalTo(port));
    }
}
