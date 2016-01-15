package org.mumue.mumue.connection;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.NoOperation;

public class ConnectionControllerTest {
    private final Configuration configuration = mock(Configuration.class);
    private final Connection connection = new Connection(configuration);
    private final ConnectionStage stage = mock(ConnectionStage.class);

    @Test
    public void prepareReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, stage).withConnection(connection);
        assertTrue(controller.prepare());
    }

    @Test
    public void executeExecutesStage() {
        ConnectionController controller = new ConnectionController(configuration, stage).withConnection(connection);
        controller.execute();

        verify(stage).execute(connection, configuration);
    }

    @Test
    public void executeMovesToNextStage() {
        ConnectionController controller = new ConnectionController(configuration, stage).withConnection(connection);
        NoOperation next = new NoOperation();
        when(stage.execute(connection, configuration)).thenReturn(next);

        controller.execute();

        assertThat(controller.getStage(), sameInstance(next));
    }

    @Test
    public void executeReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, stage).withConnection(connection);
        assertTrue(controller.execute());
    }

    @Test
    public void cleanupReturnsTrue() {
        ConnectionController controller = new ConnectionController(configuration, stage).withConnection(connection);
        assertTrue(controller.cleanup());
    }
}
