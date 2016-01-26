package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

public class NoOperationTest {
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final Connection connection = new Connection(configuration);

    @Test
    public void returnSame() {
        NoOperation stage = new NoOperation();
        assertThat(stage.execute(connection, configuration), sameInstance(stage));
    }
}
