package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

public class ThreadFactoryTest {
    private final ThreadFactory factory = new ThreadFactory();

    @Test
    public void createReturnsThread() {
        Thread thread = factory.create(new MockRunnable());
        assertNotNull(thread);
    }

    @Test
    public void createSetsName() {
        String name = RandomStringUtils.randomAlphabetic(13);
        Thread thread = factory.create(new MockRunnable(), name);
        assertNotNull(thread);
        assertEquals(name, thread.getName());
    }

    private class MockRunnable implements Runnable {
        @Override
        public void run() {}
    }
}
