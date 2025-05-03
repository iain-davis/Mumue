package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class NoOperationTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final Connection connection = new Connection(configuration);

    @Test
    public void returnSame() {
        NoOperation stage = new NoOperation();
        assertThat(stage.execute(connection, configuration), sameInstance(stage));
    }
}
