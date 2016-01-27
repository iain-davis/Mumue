package org.mumue.mumue.connection;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.states.ConnectionStateProvider;
import org.mumue.mumue.connection.states.NoOperation;
import org.mumue.mumue.connection.states.WelcomeDisplay;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

public class ConnectionControllerTest {
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final Connection connection = TestObjectBuilder.connection();
    private final ConnectionStateProvider connectionStateProvider = mock(ConnectionStateProvider.class);
    private final WelcomeDisplay stage = mock(WelcomeDisplay.class);

    @Before
    public void beforeEach() {
        when(connectionStateProvider.get(WelcomeDisplay.class)).thenReturn(stage);
    }

    @Test
    public void prepareReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, connectionStateProvider).withConnection(connection);
        assertTrue(controller.prepare());
    }

    @Test
    public void executeExecutesStage() {
        ConnectionController controller = new ConnectionController(configuration, connectionStateProvider).withConnection(connection);
        controller.execute();

        verify(stage).execute(connection, configuration);
    }

    @Test
    public void executeMovesToNextStage() {
        ConnectionController controller = new ConnectionController(configuration, connectionStateProvider).withConnection(connection);
        NoOperation next = new NoOperation();
        when(stage.execute(connection, configuration)).thenReturn(next);

        controller.execute();

        assertThat(controller.getState(), sameInstance(next));
    }

    @Test
    public void executeReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, connectionStateProvider).withConnection(connection);
        assertTrue(controller.execute());
    }

    @Test
    public void cleanupReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, connectionStateProvider).withConnection(connection);
        assertTrue(controller.cleanup());
    }
}
