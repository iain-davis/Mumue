package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.stages.ConnectionStage;
import org.ruhlendavis.meta.connection.stages.NoOperationStage;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionControllerTest {
    private final TextQueue inputQueue = new TextQueue();
    private final TextQueue outputQueue = new TextQueue();

    private final Configuration configuration = mock(Configuration.class);
    private final ConnectionStage stage = mock(ConnectionStage.class);

    @Test
    public void executeExecutesStage() {
        ConnectionController controller = new ConnectionController(inputQueue, outputQueue, configuration, stage);
        controller.prepare();
        controller.execute();
        controller.cleanup();

        verify(stage).execute(inputQueue, outputQueue, configuration);
    }

    @Test
    public void executeMovesToNextStage() {
        ConnectionController controller = new ConnectionController(inputQueue, outputQueue, configuration, stage);
        NoOperationStage next = new NoOperationStage();
        when(stage.execute(inputQueue, outputQueue, configuration)).thenReturn(next);

        controller.execute();

        assertThat(controller.getStage(), sameInstance(next));
    }
}
