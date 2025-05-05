package org.mumue.mumue.threading;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

class ThreadFactoryTest {
    private final ThreadFactory factory = new ThreadFactory();

    @Test
    void createReturnsThread() {
        Thread thread = factory.create(new MockRunnable());
        assertThat(thread, notNullValue());
    }

    @Test
    void createSetsName() {
        String name = RandomStringUtils.insecure().nextAlphabetic(13);
        Thread thread = factory.create(new MockRunnable(), name);

        assertThat(thread.getName(), equalTo(name));
    }

    @Test
    void createHandlesNullName() {
        Thread thread = factory.create(new MockRunnable(), null);

        assertThat(thread.getName(), startsWith("Thread-"));
    }

    @Test
    void createHandlesBlankName() {
        Thread thread = factory.create(new MockRunnable(), "");

        assertThat(thread.getName(), startsWith("Thread-"));
    }

    private static class MockRunnable implements Runnable {
        @Override
        public void run() {
        }
    }
}
