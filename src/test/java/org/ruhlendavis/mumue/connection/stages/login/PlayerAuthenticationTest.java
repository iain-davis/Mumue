package org.ruhlendavis.mumue.connection.stages.login;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.CurrentTimestampProvider;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.mainmenu.DisplayPlayerMenu;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.player.PlayerDao;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class PlayerAuthenticationTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock PlayerDao dao;
    @Mock TextMaker textMaker;
    @Mock CurrentTimestampProvider currentTimestampProvider;
    @Mock PlayerBuilder playerBuilder;
    @InjectMocks PlayerAuthentication stage;

    private final String loginId = RandomStringUtils.randomAlphanumeric(13);
    private final String password = RandomStringUtils.randomAlphanumeric(17);
    private final String loginFailed = RandomStringUtils.randomAlphanumeric(16);
    private final String loginSuccess = RandomStringUtils.randomAlphanumeric(16);
    private final Instant timestamp = Instant.now();
    private final Player player = new Player();
    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(password);
        when(currentTimestampProvider.get()).thenReturn(timestamp);
        when(textMaker.getText(eq(TextName.LoginFailed), anyString())).thenReturn(loginFailed);
        when(textMaker.getText(eq(TextName.LoginSuccess), anyString())).thenReturn(loginSuccess);
        when(dao.getPlayer(loginId, password)).thenReturn(player);
        when(dao.playerExistsFor(loginId)).thenReturn(true);
        when(playerBuilder.build()).thenReturn(player);
    }

    @Test
    public void executeWithValidCredentialsReturnsNextStage() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void executeWithValidCredentialsSetsPlayerOnConnection() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(connection.getPlayer(), sameInstance(player));
    }

    @Test
    public void executeWithValidCredentialsSetsLastUsed() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(player.getLastUsed(), equalTo(timestamp));
    }

    @Test
    public void executeWithValidCredentialsCountsUse() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        long expected = player.getUseCount() + 1;

        stage.execute(connection, configuration);

        assertThat(player.getUseCount(), equalTo(expected));
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

        assertThat(next, instanceOf(LoginPrompt.class));
    }

    @Test
    public void executeWithInvalidCredentialsPutsLoginFailedMessageOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(loginFailed));
    }

    @Test
    public void executeWithNewPlayerUsesBuilder() {
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        verify(playerBuilder).build();
    }

    @Test
    public void executeWithNewPlayerSetsLoginId() {
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        assertThat(connection.getPlayer().getLoginId(), equalTo(loginId));
    }

    @Test
    public void executeWithNewPlayerDisplaysMenu() {
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void executeWithNewPlayerAddsPlayer() {
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        verify(dao).createPlayer(player, password);
    }
}
