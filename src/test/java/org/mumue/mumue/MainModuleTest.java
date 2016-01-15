package org.mumue.mumue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertSame;

public class MainModuleTest {
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