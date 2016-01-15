package org.mumue.mumue;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public ExecutorService providesExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
