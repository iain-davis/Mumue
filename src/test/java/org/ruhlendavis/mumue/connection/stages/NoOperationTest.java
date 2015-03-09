package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;

public class NoOperationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    private final Connection connection = new Connection(configuration);

    @Test
    public void returnSame() {
        NoOperation stage = new NoOperation();
        assertThat(stage.execute(connection, configuration), sameInstance(stage));
    }
}
