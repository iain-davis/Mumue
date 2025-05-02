package org.mumue.mumue.configuration.online;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ConfigurationDefaults;

public class OnlineConfigurationTest {
    private final OnlineConfigurationDao dao = mock(OnlineConfigurationDao.class);
    private final OnlineConfiguration onlineConfiguration = new OnlineConfiguration(dao);

    @Test
    public void getServerLocaleReturnsDefault() {
        when(dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE)).thenReturn("");
        assertThat(onlineConfiguration.getServerLocale(), equalTo(ConfigurationDefaults.SERVER_LOCALE));
    }

    @Test
    public void getServerLocaleReturnsServerLocale() {
        String serverLocale = RandomStringUtils.insecure().nextAlphabetic(13);
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
}
