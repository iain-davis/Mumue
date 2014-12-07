package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ThreadFactoryTest {
    private final ThreadFactory factory = new ThreadFactory();

    @Test
    public void createThreadReturnsThread() {
        Thread thread = factory.createThread(new Connection(), RandomStringUtils.randomAlphabetic(13));
        assertNotNull(thread);
    }

    @Test
    public void createThreadSetsName() {
        String name = RandomStringUtils.randomAlphabetic(13);
        Thread thread = factory.createThread(new Connection(), name);
        assertEquals(name, thread.getName());
    }
}
