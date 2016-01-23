package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class NoOperationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock ApplicationConfiguration configuration;
    private final Connection connection = new Connection(configuration);

    @Test
    public void returnSame() {
        NoOperation stage = new NoOperation();
        assertThat(stage.execute(connection, configuration), sameInstance(stage));
    }
}
