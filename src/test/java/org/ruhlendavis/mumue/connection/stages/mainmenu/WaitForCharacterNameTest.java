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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.components.GameCharacterDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.NoOperation;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class WaitForCharacterNameTest {
    private final String name = RandomStringUtils.randomAlphabetic(17);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final String loginId = RandomStringUtils.randomAlphabetic(14);
    private final Player player = new Player().withLocale(locale).withLoginId(loginId);
    private final GameCharacter character = new GameCharacter().withPlayerId(loginId);

    private final Connection connection = new Connection().withPlayer(player).withCharacter(character);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock GameCharacterDao dao;
    @InjectMocks WaitForCharacterName stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(dao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(new GameCharacter());
        when(dao.getCharacter(name)).thenReturn(character);

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

        assertThat(next, instanceOf(NoOperation.class));
    }

    @Test
    public void nextStageOnValidNameNoMatchingName() {
        when(dao.getCharacter(name)).thenReturn(new GameCharacter());
        connection.getInputQueue().push(name);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NoOperation.class));
    }

    @Test
    public void setCharacterName() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getName(), equalTo(name));
    }

    @Test
    public void addCharacterToDatabase() {
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        verify(dao).addCharacter(character);
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
    public void nameTakenInUniverseDisplayMessage() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter();
        characterThatExists.setId(RandomUtils.nextLong(300, 400));

        connection.getInputQueue().push(name);

        when(dao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
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

        when(dao.getCharacter(name, connection.getCharacter().getUniverseId())).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameAlreadyExists, locale, serverLocale)).thenReturn(message);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    public void nameTakenByOtherPlayerDisplayMessage() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter().withId(RandomUtils.nextLong(200, 300))
                .withPlayerId(RandomStringUtils.randomAlphabetic(7));

        connection.getInputQueue().push(name);

        when(dao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, locale, serverLocale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void nameTakenByOtherPlayerRePrompt() {
        String message = RandomStringUtils.randomAlphabetic(16);
        GameCharacter characterThatExists = new GameCharacter().withId(RandomUtils.nextLong(200, 300))
                .withPlayerId(RandomStringUtils.randomAlphabetic(7));
        connection.getInputQueue().push(name);

        when(dao.getCharacter(name)).thenReturn(characterThatExists);
        when(textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, locale, serverLocale)).thenReturn(message);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }
}
