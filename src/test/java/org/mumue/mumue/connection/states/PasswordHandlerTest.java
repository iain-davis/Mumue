package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class PasswordHandlerTest {
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);
    private final PasswordHandler passwordHandler = new PasswordHandler(connectionStateProvider);

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionState next = passwordHandler.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordHandler.class));
    }

    @Test
    public void executeWithTwoInputReturnsAuthenticationStage() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(17));
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(17));

        ConnectionState next = passwordHandler.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerAuthentication.class));
    }
}
