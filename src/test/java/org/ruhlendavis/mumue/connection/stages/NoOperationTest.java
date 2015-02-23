package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;

@RunWith(MockitoJUnitRunner.class)
public class NoOperationTest {
    private final Connection connection = new Connection();
    @Mock Configuration configuration;

    @Test
    public void returnSame() {
        NoOperation stage = new NoOperation();
        assertThat(stage.execute(connection, configuration), sameInstance(stage));
    }
}
