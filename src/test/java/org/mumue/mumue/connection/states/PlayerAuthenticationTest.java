package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import java.time.Instant;
import java.util.Locale;
import java.util.Random;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerAuthenticationTest {
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final CurrentTimestampProvider currentTimestampProvider = mock(CurrentTimestampProvider.class);
    private final PlayerRepository playerRepository = mock(PlayerRepository.class);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final PlayerAuthentication stage = new PlayerAuthentication(connectionStateProvider, new PlayerBuilder(), playerRepository, textMaker);

    private final String loginId = RandomStringUtils.randomAlphanumeric(13);
    private final String password = RandomStringUtils.randomAlphanumeric(17);
    private final String loginFailed = "FAILED: " + RandomStringUtils.randomAlphanumeric(16);
    private final String loginSuccess = "SUCCESS: " + RandomStringUtils.randomAlphanumeric(16);
    private final Instant timestamp = Instant.now();
    private final Player player = Nimue.player().withId(new Random().nextInt(100) + 10).build();
    private final Connection connection = Nimue.connection();

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(password);
        when(configuration.getServerLocale()).thenReturn(Locale.ENGLISH.toString());
        when(currentTimestampProvider.get()).thenReturn(timestamp);
        when(textMaker.getText(eq(TextName.LoginFailed), anyString())).thenReturn(loginFailed);
        when(textMaker.getText(eq(TextName.LoginSuccess), anyString())).thenReturn(loginSuccess);
        when(playerRepository.get(loginId)).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);
    }

    @Test
    public void executeWithValidCredentialsReturnsNextState() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerConnected.class));
    }

    @Test
    public void executeWithValidCredentialsSetsPlayerOnConnection() {
        stage.execute(connection, configuration);

        assertThat(connection.getPlayer(), sameInstance(player));
    }

    @Test
    public void executeWithValidCredentialsPutsLoginSuccessMessageOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(loginSuccess));
    }

    @Test
    public void executeWithInvalidCredentialsReturnsLoginPromptStage() {
        when(playerRepository.get(loginId, password)).thenReturn(new Player());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginIdPrompt.class));
    }

    @Test
    public void executeWithInvalidCredentialsPutsLoginFailedMessageOnOutputQueue() {
        when(playerRepository.get(loginId, password)).thenReturn(new Player());

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(loginFailed));
    }

    @Test
    public void executeWithNewPlayerSetsLoginId() {
        when(playerRepository.get(loginId)).thenReturn(new Player());

        stage.execute(connection, configuration);

        assertThat(connection.getPlayer().getLoginId(), equalTo(loginId));
    }

}
