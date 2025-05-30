package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CharacterNameHandlerTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);

    private final String loginId = RandomStringUtils.insecure().nextAlphabetic(14);
    private final String name = RandomStringUtils.insecure().nextAlphabetic(17);
    private final long playerId = RandomUtils.insecure().randomLong(100, 200);
    private final long locationId = RandomUtils.insecure().randomLong(200, 300);
    private final Player player = new PlayerBuilder().withId(playerId).withLoginId(loginId).build();
    private final GameCharacter character = new GameCharacter();
    private final Universe universe = new UniverseBuilder().withStartingSpaceId(locationId).build();
    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final CharacterNameHandler stage = new CharacterNameHandler(connectionStateProvider, characterDao, textMaker, universeRepository);

    @BeforeEach
    void beforeEach() {
        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(character);
        when(universeRepository.getUniverse(character.getUniverseId())).thenReturn(universe);

    }

    @Test
    void neverReturnNull() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, notNullValue());
    }

    @Test
    void continueWaitOnNoInput() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    void nextStageOnValidName() {
        connection.getInputQueue().push(name);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    void nextStageOnValidNameNoMatchingName() {
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());
        connection.getInputQueue().push(name);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    void setCharacterName() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getName(), equalTo(name));
    }

    @Test
    void setComponentId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
    }

    @Test
    void setPlayerId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getPlayerId(), equalTo(playerId));
    }

    @Test
    void setLocationId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getLocationId(), equalTo(locationId));
    }

    @Test
    void setHomeId() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getHomeLocationId(), equalTo(locationId));
    }

    @Test
    void addCharacterToDatabase() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        verify(characterDao).createCharacter(character);
    }

    @Test
    void emptyNameDisplayMenu() {
        String name = "";
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    void whitespaceNameDisplayMenu() {
        String name = "   ";
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    void nameTakenInUniverseDisplayMessage() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.insecure().randomLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    void nameTakenInUniverseRePrompt() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.insecure().randomLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    void nameTakenByOtherPlayerDisplayMessage() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.insecure().randomLong(100, 200))
                .withId(RandomUtils.insecure().randomLong(200, 300)).build();

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    void nameTakenByOtherPlayerRePrompt() {
        String message = RandomStringUtils.insecure().nextAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.insecure().randomLong(600, 700))
                .withId(RandomUtils.insecure().randomLong(200, 300)).build();
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }
}
