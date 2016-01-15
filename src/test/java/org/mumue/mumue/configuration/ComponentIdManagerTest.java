package org.mumue.mumue.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class ComponentIdManagerTest {
    @Mock OnlineConfiguration onlineConfiguration;
    @InjectMocks ComponentIdManager componentIdManager;

    @Test
    public void returnsGivenLong() {
        long id = RandomUtils.nextLong(100, 200);
        when(onlineConfiguration.getLastComponentId()).thenReturn(id - 1);

        assertThat(componentIdManager.getNewComponentId(onlineConfiguration), equalTo(id));
    }
}
