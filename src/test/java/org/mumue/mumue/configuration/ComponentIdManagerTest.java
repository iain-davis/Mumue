package org.mumue.mumue.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

public class ComponentIdManagerTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock OnlineConfiguration onlineConfiguration;
    @InjectMocks ComponentIdManager componentIdManager;

    @Test
    public void returnsGivenLong() {
        long id = RandomUtils.insecure().randomLong(100, 200);
        when(onlineConfiguration.getLastComponentId()).thenReturn(id - 1);

        assertThat(componentIdManager.getNewComponentId(), equalTo(id));
    }
}
