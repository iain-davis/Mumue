package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterNameHandlerTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);

    private final String loginId = RandomStringUtils.insecure().nextAlphabetic(14);
    private final String name = RandomStringUtils.insecure().nextAlphabetic(17);
    private final long playerId = RandomUtils.nextLong(100, 200);
    private final long locationId = RandomUtils.nextLong(200, 300);
    private final Player player = new PlayerBuilder().withId(playerId).withLoginId(loginId).build();
    private final GameCharacter character = new GameCharacter();
    private final Universe universe = new UniverseBuilder().withStartingSpaceId(locationId).build();
    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final CharacterNameHandler stage = new CharacterNameHandler(connectionStateProvider, characterDao, textMaker, universeRepository);

    @Before
    public void beforeEach() {
        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(character);
        when(universeRepository.getUniverse(character.getUniverseId())).thenReturn(universe);

    }

    @Test
    public void neverReturnNull() {
        ConnectionState next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void nextStageOnValidName() {
        connection.getInputQueue().push(name);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void nextStageOnValidNameNoMatchingName() {
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());
        connection.getInputQueue().push(name);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void setCharacterName() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getName(), equalTo(name));
    }

    @Test
    public void setComponentId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
    }

    @Test
    public void setPlayerId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getPlayerId(), equalTo(playerId));
    }

    @Test
    public void setLocationId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getLocationId(), equalTo(locationId));
    }

    @Test
    public void setHomeId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getHomeLocationId(), equalTo(locationId));
    }

    @Test
    public void addCharacterToDatabase() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        verify(characterDao).createCharacter(character);
    }

    @Test
    public void emptyNameDisplayMenu() {
        String name = "";
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void whitespaceNameDisplayMenu() {
        String name = "   ";
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void nameTakenInUniverseDisplayMessage() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.nextLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void nameTakenInUniverseRePrompt() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.nextLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    public void nameTakenByOtherPlayerDisplayMessage() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.nextLong(100, 200))
                .withId(RandomUtils.nextLong(200, 300)).build();

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void nameTakenByOtherPlayerRePrompt() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.nextLong(600, 700))
                .withId(RandomUtils.nextLong(200, 300)).build();
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }
}
