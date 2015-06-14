package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerDao;

@RunWith(MockitoJUnitRunner.class)
public class WaitForLoginIdTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Player player;
    @Mock Configuration configuration;
    @Mock PlayerDao dao;
    @InjectMocks WaitForLoginId stage;

    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
    }

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForLoginId.class));
    }

    @Test
    public void executeWithValidLoginIdPromptsForPassword() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(true);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void executeWithInvalidLoginIdPromptsForNewPlayer() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        ConnectionStage next = stage.execute(connection, configuration);

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
