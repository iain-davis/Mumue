package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class LoginIdHandlerTest {
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final PlayerRepository playerRepository = mock(PlayerRepository.class);
    private final Connection connection = new Connection(configuration);
    private final LoginIdHandler stage = new LoginIdHandler(connectionStateProvider, playerRepository);

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginIdHandler.class));
    }

    @Test
    public void executeWithValidLoginIdPromptsForPassword() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(loginId);

        when(playerRepository.get(loginId)).thenReturn(Nimue.player().withId(1L).build());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void executeWithInvalidLoginIdPromptsForNewPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(playerRepository.get(loginId)).thenReturn(Nimue.player().build());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NewPlayerPrompt.class));
    }

    @Test
    public void executeWithValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(playerRepository.get(loginId)).thenReturn(Nimue.player().withId(1L).build());

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void executeWithInValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(playerRepository.get(loginId)).thenReturn(Nimue.player().build());

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }
}
