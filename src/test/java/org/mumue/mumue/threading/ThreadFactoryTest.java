package org.mumue.mumue.threading;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

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

    @Test
    public void createHandlesNullName() {
        Thread thread = factory.create(new MockRunnable(), null);
        assertNotNull(thread);
        assertThat(thread.getName(), startsWith("Thread-"));
    }

    @Test
    public void createHandlesBlankName() {
        Thread thread = factory.create(new MockRunnable(), "");
        assertNotNull(thread);
        assertThat(thread.getName(), startsWith("Thread-"));
    }

    private class MockRunnable implements Runnable {
        @Override
        public void run() {
        }
    }
}
