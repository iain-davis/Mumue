package org.mumue.mumue.connection.states;

import jakarta.inject.Inject;

import com.google.inject.Injector;

public class ConnectionStateProvider {
    private final Injector injector;

    @Inject
    public ConnectionStateProvider(Injector injector) {
        this.injector = injector;
    }

    public ConnectionState get(Class<? extends ConnectionState> stateClass) {
        if (stateClass == null) {
            return injector.getInstance(NoOperation.class);
        }
        return injector.getInstance(stateClass);
    }
}
