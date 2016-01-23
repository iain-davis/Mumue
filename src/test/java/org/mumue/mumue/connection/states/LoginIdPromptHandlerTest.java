package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.PlayerDao;

public class LoginIdPromptHandlerTest {
    private final StateCollection stateCollection = mock(StateCollection.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final PlayerDao dao = mock(PlayerDao.class);
    private final Connection connection = new Connection(configuration);
    private final LoginIdPromptHandler stage = new LoginIdPromptHandler(stateCollection, dao);

    @Before
    public void beforeEach() {
        when(stateCollection.get(StateName.PasswordPrompt)).thenReturn(new PasswordPrompt(stateCollection, null));
        when(stateCollection.get(StateName.NewPlayerPrompt)).thenReturn(new NewPlayerPrompt(null, null));
    }

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginIdPromptHandler.class));
    }

    @Test
    public void executeWithValidLoginIdPromptsForPassword() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(true);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void executeWithInvalidLoginIdPromptsForNewPlayer() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NewPlayerPrompt.class));
    }

    @Test
    public void executeWithValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void executeWithInValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }
}
