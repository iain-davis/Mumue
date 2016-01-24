package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandDrivenPromptHandlerTest {
    private static final Random RANDOM = new Random();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final PlayerRepository playerRepository = mock(PlayerRepository.class);

    private final CommandDrivenPromptHandler stage = new CommandDrivenPromptHandler(mock(PlayerConnected.class), characterDao, playerRepository, textMaker);

    @Test
    public void connectCommand() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(14);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(PlayerConnected.class));
    }

    @Test
    public void connectCommandPutsCharacterOnConnection() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        stage.execute(connection, configuration);

        assertSame(connection.getCharacter(), character);
    }

    @Test
    public void connectCommandPutsPlayerOnConnection() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);


        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        stage.execute(connection, configuration);

        assertSame(connection.getPlayer(), player);
    }

    @Test
    public void connectCommandWorksMixedCase() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);

        connection.getInputQueue().push("ConNeCt " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(PlayerConnected.class));
    }

    @Test
    public void connectCommandWorksTruncated() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(loginId, password)).thenReturn(player);

        connection.getInputQueue().push("con " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

        assertThat(returned, instanceOf(PlayerConnected.class));
    }

    @Test
    public void rejectConnectWhenMissingPassword() {
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String text = RandomStringUtils.randomAlphabetic(13);
        GameCharacter character = createCharacter();
        Player player = createPlayer(loginId);

        when(characterDao.getCharacter(characterName)).thenReturn(character);
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.MissingPassword, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("con " + characterName);
        ConnectionState returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void rejectConnectWhenMissingCharacterName() {
        String text = RandomStringUtils.randomAlphabetic(13);

        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.MissingCharacterName, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("con");
        ConnectionState returned = stage.execute(connection, configuration);

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
        when(playerRepository.get(character.getPlayerId())).thenReturn(new Player());
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(6) + " " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    public void rejectWhenCharacterDoesNotExist() {
        String text = RandomStringUtils.randomAlphabetic(13);
        String characterName = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);

        when(characterDao.getCharacter(characterName)).thenReturn(new GameCharacter());
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.CharacterDoesNotExist, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

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
        when(playerRepository.get(character.getPlayerId())).thenReturn(player);
        when(playerRepository.get(login, password)).thenReturn(new Player());
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(textMaker.getText(TextName.LoginFailed, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        connection.getInputQueue().push("connect " + characterName + " " + password);
        ConnectionState returned = stage.execute(connection, configuration);

        assertSame(returned, stage);
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    private GameCharacter createCharacter() {
        return TestObjectBuilder.character().withId(RANDOM.nextInt(1000)).withPlayerId(RANDOM.nextInt(1000) + 1000).build();
    }

    private Player createPlayer(String loginId) {
        Player player = new Player();
        player.setId(RANDOM.nextInt(100) + 1);
        player.setLoginId(loginId);
        return player;
    }
}