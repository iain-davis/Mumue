package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

public class ThreadFactoryTest {
    private final ThreadFactory factory = new ThreadFactory();

    @Test
    public void createThreadReturnsThread() {
        Thread thread = factory.createThread(new ConnectionInputReceiver(), RandomStringUtils.randomAlphabetic(13));
        assertNotNull(thread);
    }

    @Test
    public void createThreadSetsName() {
        String name = RandomStringUtils.randomAlphabetic(13);
        Thread thread = factory.createThread(new ConnectionInputReceiver(), name);
        assertEquals(name, thread.getName());
    }
}
