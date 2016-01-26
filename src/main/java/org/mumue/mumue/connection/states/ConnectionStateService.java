package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;

public class ConnectionStateService {
    private final Injector injector;

    @Inject
    public ConnectionStateService(Injector injector) {
        this.injector = injector;
    }

    public ConnectionState get(Class<? extends ConnectionState> stateClass) {
        if (stateClass == null) {
            return injector.getInstance(NoOperation.class);
        }
        return injector.getInstance(stateClass);
    }
}
