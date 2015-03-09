package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.components.character.CharacterBuilder;
import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.components.universe.Universe;
import org.ruhlendavis.mumue.components.universe.UniverseBuilder;
import org.ruhlendavis.mumue.components.universe.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterNameTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock CharacterDao characterDao;
    @Mock UniverseDao universeDao;
    @InjectMocks WaitForCharacterName stage;

    private final String name = RandomStringUtils.randomAlphabetic(17);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final String loginId = RandomStringUtils.randomAlphabetic(14);
    private final long playerId = RandomUtils.nextLong(100, 200);
    private final long locationId = RandomUtils.nextLong(200, 300);
    private final Player player = new PlayerBuilder().withId(playerId).withLocale(locale).withLoginId(loginId).build();
    private final GameCharacter character = new GameCharacter();
    private final Universe universe = new UniverseBuilder().withStartingSpaceId(locationId).build();

    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(characterDao.getCharacter(name)).thenReturn(character);
        when(universeDao.getUniverse(character.getUniverseId())).thenReturn(universe);

    }

    @Test
    public void neverReturnNull() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void nextStageOnValidName() {
        connection.getInputQueue().push(name);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void nextStageOnValidNameNoMatchingName() {
        when(characterDao.getCharacter(name)).thenReturn(new GameCharacter());
        connection.getInputQueue().push(name);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void setCharacterName() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getName(), equalTo(name));
    }

    @Test
    public void setComponentId() {
        long id = RandomUtils.nextLong(100, 200);
        connection.getInputQueue().push(name);
        when(configuration.getNewComponentId()).thenReturn(id);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getId(), equalTo(id));
    }

    @Test
    public void setPlayerId() {
        long id = RandomUtils.nextLong(100, 200);
        connection.getInputQueue().push(name);
        when(configuration.getNewComponentId()).thenReturn(id);

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
    public void nameTakenInUniverseDisplayMessage() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.nextLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, locale, serverLocale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void nameTakenInUniverseRePrompt() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.nextLong(300, 400));

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, locale, serverLocale)).thenReturn(message);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    public void nameTakenByOtherPlayerDisplayMessage() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.nextLong(100, 200))
                .withId(RandomUtils.nextLong(200, 300)).build();

        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, locale, serverLocale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void nameTakenByOtherPlayerRePrompt() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new CharacterBuilder().withPlayerId(RandomUtils.nextLong(600, 700))
                .withId(RandomUtils.nextLong(200, 300)).build();
        connection.getInputQueue().push(name);

        when(characterDao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, locale, serverLocale)).thenReturn(message);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }
}
