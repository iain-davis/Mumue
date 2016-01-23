package org.mumue.mumue.connection.states;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import com.google.inject.Injector;

public class StateCollection {
    private static final Map<StateName, ConnectionState> states = new HashMap<>();
    private final Injector injector;

    @Inject
    public StateCollection(Injector injector) {
        this.injector = injector;
    }

    public ConnectionState get(StateName name) {
        if (name == null) {
            name = StateName.NoOperationState;
        }
        if (!states.containsKey(name)) {
            states.put(name, injector.getInstance(name.getStateClass()));
        }
        return states.get(name);
    }
}
