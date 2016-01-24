package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerAuthenticationTest {
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final PlayerDao playerDao = mock(PlayerDao.class);
    private final CurrentTimestampProvider currentTimestampProvider = mock(CurrentTimestampProvider.class);
    private final PlayerBuilder playerBuilder = mock(PlayerBuilder.class);
    private final PlayerRepository playerRepository = mock(PlayerRepository.class);
    private final PlayerAuthentication stage = new PlayerAuthentication(mock(LoginIdPrompt.class), new PlayerBuilder(), mock(PlayerConnected.class), playerDao, playerRepository, textMaker);

    private final String loginId = RandomStringUtils.randomAlphanumeric(13);
    private final String password = RandomStringUtils.randomAlphanumeric(17);
    private final String loginFailed = "FAILED: " + RandomStringUtils.randomAlphanumeric(16);
    private final String loginSuccess = "SUCCESS: " + RandomStringUtils.randomAlphanumeric(16);
    private final Instant timestamp = Instant.now();
    private final Player player = TestObjectBuilder.player().withId(new Random().nextInt(100) + 10).build();
    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(password);
        when(currentTimestampProvider.get()).thenReturn(timestamp);
        when(textMaker.getText(Matchers.eq(TextName.LoginFailed), anyString())).thenReturn(loginFailed);
        when(textMaker.getText(eq(TextName.LoginSuccess), anyString())).thenReturn(loginSuccess);
        when(playerRepository.get(loginId, password)).thenReturn(player);
        when(playerDao.playerExistsFor(loginId)).thenReturn(true);
        when(playerBuilder.build()).thenReturn(player);
        when(playerBuilder.withLoginId(anyString())).thenReturn(playerBuilder);
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
        when(playerDao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        assertThat(connection.getPlayer().getLoginId(), equalTo(loginId));
    }

}
