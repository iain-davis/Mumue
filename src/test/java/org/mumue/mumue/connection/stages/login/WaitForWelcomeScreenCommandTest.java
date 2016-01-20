package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.Random;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.playing.EnterUniverse;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForWelcomeScreenCommandTest {
    private static final Random RANDOM = new Random();
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));

    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final PlayerDao playerDao = mock(PlayerDao.class);
    private final WaitForWelcomeScreenCommand stage = new WaitForWelcomeScreenCommand(injector, characterDao, playerDao, textMaker);

    @Test
    public void connectCommand() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(EnterUniverse.class));
    }

    @Test
    public void connectCommandWorksMixedCase() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);

        connection.getInputQueue().push("ConNeCt " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(EnterUniverse.class));
    }

    @Test
    public void connectCommandWorksTruncated() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);

        connection.getInputQueue().push("con " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(EnterUniverse.class));
    }

    @Test
    public void rejectConnectWhenMissingPassword() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String text = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.MissingPassword, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("con " + characterName);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void rejectConnectWhenMissingCharacterName() {
        String text = RandomStringUtils.randomAlphabetic(13);
        String characterName = RandomStringUtils.randomAlphabetic(16);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.MissingCharacterName, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("con");
        ConnectionStage returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void rejectWhenInvalidCommand() {
        String text = RandomStringUtils.randomAlphabetic(13);
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(6) + " " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void rejectWhenCharacterDoesNotExist() {
        String text = RandomStringUtils.randomAlphabetic(13);
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        when(characterDao.getCharacter(characterName)).thenReturn(new GameCharacter());
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(new Player());
        when(playerDao.authenticate(anyString(), anyString())).thenReturn(true);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.CharacterDoesNotExist, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void authenticatePlayerUsingInvalidPassword() {
        String text = RandomStringUtils.randomAlphabetic(13);
        String login = RandomStringUtils.randomAlphabetic(5);
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();

        Player player = new Player();
        player.setLoginId(login);
        player.setId(character.getPlayerId());

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerDao.getPlayer(character.getPlayerId())).thenReturn(player);
        when(playerDao.authenticate(login, password)).thenReturn(false);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.LoginFailed, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionStage returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    private GameCharacter createCharacter() {
        return TestObjectBuilder.character().withId(RANDOM.nextInt(1000)).withPlayerId(RANDOM.nextInt(1000) + 1000).build();
    }
}