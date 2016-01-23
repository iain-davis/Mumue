package org.mumue.mumue.connection.states;

public enum StateName {
    DisplayLoginPrompt(org.mumue.mumue.connection.states.DisplayLoginPrompt.class),
    DisplayWelcome(DisplayWelcome.class),
    NoOperationState(NoOperation.class)
    ;

    private final Class<? extends ConnectionState> stateClass;

    StateName(Class<? extends ConnectionState> stateClass) {
        this.stateClass = stateClass;
    }

    public Class<? extends ConnectionState> getStateClass() {
        return stateClass;
    }
}
