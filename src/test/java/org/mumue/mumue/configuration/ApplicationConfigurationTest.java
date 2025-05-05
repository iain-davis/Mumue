package org.mumue.mumue.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

class ApplicationConfigurationTest {
    private final OnlineConfiguration onlineConfiguration = mock(OnlineConfiguration.class);
    private final ComponentIdManager componentIdManager = mock(ComponentIdManager.class);
    private final ApplicationConfiguration configuration = new ApplicationConfiguration(onlineConfiguration, componentIdManager);

    @Test
    void getServerLocale() {
        String serverLocale = RandomStringUtils.insecure().nextAlphabetic(5);
        when(onlineConfiguration.getServerLocale()).thenReturn(serverLocale);
        assertThat(configuration.getServerLocale(), equalTo(serverLocale));
    }

    @Test
    void getNewComponentId() {
        long id = RandomUtils.insecure().randomLong(100, 200);
        when(componentIdManager.getNewComponentId()).thenReturn(id);
        assertThat(configuration.getNewComponentId(), equalTo(id));
    }
}
