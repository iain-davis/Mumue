package org.mumue.mumue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public ExecutorService providesExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
