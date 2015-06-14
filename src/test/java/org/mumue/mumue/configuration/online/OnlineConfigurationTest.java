package org.mumue.mumue.configuration.online;

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

import org.mumue.mumue.configuration.ConfigurationDefaults;

public class OnlineConfigurationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock OnlineConfigurationDao dao;
    @InjectMocks OnlineConfiguration onlineConfiguration;

    @Test
    public void getServerLocaleReturnsDefault() {
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
        assertThat(onlineConfiguration.getLastComponentId(), equalTo(ConfigurationDefaults.LAST_COMPONENT_ID));

    }

    @Test
    public void getLastComponentIdReturnsLastComponentId() {
        Long lastComponentId = RandomUtils.nextLong(100, 200);
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.LAST_COMPONENT_ID)).thenReturn(lastComponentId.toString());
        assertThat(onlineConfiguration.getLastComponentId(), equalTo(lastComponentId));
    }
}
