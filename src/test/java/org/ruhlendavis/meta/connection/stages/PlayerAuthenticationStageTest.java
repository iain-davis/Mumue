package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.Connection;
import org.ruhlendavis.meta.player.Player;
import org.ruhlendavis.meta.player.PlayerDao;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class PlayerAuthenticationStageTest {
    private final String loginId = RandomStringUtils.randomAlphanumeric(13);
    private final String password = RandomStringUtils.randomAlphanumeric(17);
    private final String loginFailed = RandomStringUtils.randomAlphanumeric(16);
    private final String loginSuccess = RandomStringUtils.randomAlphanumeric(16);
    private final Player player = new Player();

    private final Connection connection = new Connection();

    @Mock Configuration configuration;
    @Mock PlayerDao dao;
    @Mock TextMaker textMaker;
    @InjectMocks PlayerAuthenticationStage stage;

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(password);
        when(textMaker.getText(eq(TextName.LoginFailed), anyString())).thenReturn(loginFailed);
        when(textMaker.getText(eq(TextName.LoginSuccess), anyString())).thenReturn(loginSuccess);
        when(dao.getPlayer(loginId, password)).thenReturn(player);
    }

    @Test
    public void executeWithValidCredentialsReturnsNextStage() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NoOperationStage.class));
    }

    @Test
    public void executeWithValidCredentialsSetsPlayerOnConnection() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(connection.getPlayer(), sameInstance(player));
    }

    @Test
    public void executeWithValidCredentialsPutsLoginSuccessMessageOnOutputQueue() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(loginSuccess));
    }

    @Test
    public void executeWithInvalidCredentialsReturnsLoginPromptStage() {
        when(dao.authenticate(loginId, password)).thenReturn(false);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginPromptStage.class));
    }

    @Test
    public void executeWithInvalidCredentialsPutsLoginFailedMessageOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(loginFailed));
    }
}
