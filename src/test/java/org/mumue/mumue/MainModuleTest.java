package org.mumue.mumue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertSame;

import java.util.concurrent.ExecutorService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

public class MainModuleTest {
    @Test
    public void providesExecutorServiceNeverReturnsNull() {
        assertThat(new MainModule().providesExecutorService(), notNullValue());
    }

    @Test
    public void instantiateExecutorService() {
        Injector injector = Guice.createInjector(new MainModule());
        injector.getInstance(ExecutorService.class);
    }

    @Test
    public void singletonExecutorService() {
        Injector injector = Guice.createInjector(new MainModule());
        ExecutorService instance1 = injector.getInstance(ExecutorService.class);
        ExecutorService instance2 = injector.getInstance(ExecutorService.class);

        assertSame(instance1, instance2);
    }
}