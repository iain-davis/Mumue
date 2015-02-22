package org.ruhlendavis.mumue.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class ComponentIdManagerTest {
    @Test
    public void returnsGivenLong() {
        ComponentIdManager componentIdManager = new ComponentIdManager();
        long id = RandomUtils.nextLong(100, 200);

        assertThat(componentIdManager.getNewComponentId(id), equalTo(id));
    }
}
