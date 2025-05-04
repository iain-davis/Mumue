package org.mumue.mumue.configuration;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ComponentIdManagerTest {
    private final OnlineConfiguration onlineConfiguration = mock(OnlineConfiguration.class);
    private final ComponentIdManager componentIdManager = new ComponentIdManager(onlineConfiguration);

    @Test
    void returnsGivenLong() {
        long id = RandomUtils.insecure().randomLong(100, 200);
        when(onlineConfiguration.getLastComponentId()).thenReturn(id - 1);

        assertThat(componentIdManager.getNewComponentId(), equalTo(id));
    }
}
