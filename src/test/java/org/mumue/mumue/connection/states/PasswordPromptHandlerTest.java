package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class PasswordPromptHandlerTest {
    private final StateCollection stateCollection = mock(StateCollection.class);
    private final PasswordPromptHandler stage = new PasswordPromptHandler(stateCollection);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
        when(stateCollection.get(StateName.PlayerAuthentication)).thenReturn(new PlayerAuthentication(null, null, null, null, null));
    }

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPromptHandler.class));
    }

    @Test
    public void executeWithTwoInputReturnsAuthenticationStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerAuthentication.class));
    }
}
