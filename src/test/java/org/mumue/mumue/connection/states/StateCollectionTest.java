package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;

public class StateCollectionTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final StateCollection stateCollection = new StateCollection(injector);

    @Test
    public void providesNeverReturnsNull() {
        ConnectionState state = stateCollection.get(null);
        assertThat(state, notNullValue());
    }

    @Test
    public void statesCollectionContainsWelcome() {
        ConnectionState state = stateCollection.get(StateName.WelcomeDisplay);

        assertThat(state, instanceOf(WelcomeDisplay.class));
    }

    @Test
    public void statesCollectionCachesStates() {
        ConnectionState state1 = stateCollection.get(StateName.WelcomeDisplay);
        ConnectionState state2 = stateCollection.get(StateName.WelcomeDisplay);

        assertThat(state1, sameInstance(state2));
    }

    @Test
    public void statesCollectionContainsLoginPrompt() {
        ConnectionState state = stateCollection.get(StateName.LoginIdPrompt);

        assertThat(state, instanceOf(LoginIdPrompt.class));
    }
}