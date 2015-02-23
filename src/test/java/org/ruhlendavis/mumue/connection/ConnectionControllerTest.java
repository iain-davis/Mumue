package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.NoOperation;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionControllerTest {
    private final Connection connection = new Connection();
    private final Configuration configuration = mock(Configuration.class);
    private final ConnectionStage stage = mock(ConnectionStage.class);

    @Test
    public void executeExecutesStage() {
        ConnectionController controller = new ConnectionController(connection, configuration, stage);
        controller.prepare();
        controller.execute();
        controller.cleanup();

        verify(stage).execute(connection, configuration);
    }

    @Test
    public void executeMovesToNextStage() {
        ConnectionController controller = new ConnectionController(connection, configuration, stage);
        NoOperation next = new NoOperation();
        when(stage.execute(connection, configuration)).thenReturn(next);

        controller.execute();

        assertThat(controller.getStage(), sameInstance(next));
    }
}
