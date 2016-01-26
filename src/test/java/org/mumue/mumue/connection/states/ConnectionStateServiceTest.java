package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;

public class ConnectionStateServiceTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final ConnectionStateService connectionStateService = new ConnectionStateService(injector);

    @Test
    public void providesNeverReturnsNull() {
        ConnectionState state = connectionStateService.get(null);
        assertThat(state, notNullValue());
    }

    @Test
    public void statesCollectionContainsWelcome() {
        ConnectionState state = connectionStateService.get(WelcomeDisplay.class);

        assertThat(state, instanceOf(WelcomeDisplay.class));
    }
}