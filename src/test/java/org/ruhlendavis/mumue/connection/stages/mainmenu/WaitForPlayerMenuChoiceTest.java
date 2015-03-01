package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class WaitForPlayerMenuChoiceTest {
    String locale = RandomStringUtils.randomAlphabetic(16);
    String serverLocale = RandomStringUtils.randomAlphabetic(15);
    long id = RandomUtils.nextLong(100, 200);
    private final Player player = new PlayerBuilder().withId(id).withLocale(locale).build();
    private final Connection connection = new Connection().withPlayer(player);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock CharacterDao dao = new CharacterDao();
    @InjectMocks WaitForPlayerMenuChoice stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
    }

    @Test
    public void neverReturnNull() {
        assertNotNull(stage.execute(connection, configuration));
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void goToUniverseSelectionOnCInput() {
        connection.getInputQueue().push("C");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void acceptLowerCase() {
        connection.getInputQueue().push("c");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void playerSelectsPWithoutCharactersGoToUniverseSelectionPrompt() {
        connection.getInputQueue().push("P");
        when(textMaker.getText(TextName.CharacterNeeded, locale, serverLocale)).thenReturn(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void playerSelectsPWithoutCharactersPutCharacterNeededMessageOnQueue() {
        String message = RandomStringUtils.randomAlphabetic(17);
        when(textMaker.getText(TextName.CharacterNeeded, locale, serverLocale)).thenReturn(message);
        connection.getInputQueue().push("P");

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));

    }
    @Test
    public void playerHasCharactersAndSelectsPGoToCharacterSelectionPrompt() {
        List<GameCharacter> characters = new ArrayList<>();
        characters.add(new GameCharacter());
        when(dao.getCharacters(player.getId())).thenReturn(characters);
        connection.getInputQueue().push("P");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    public void redisplayMenuOnInvalidInput() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(textMaker.getText(TextName.InvalidOption, locale, serverLocale)).thenReturn("");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void displayInvalidOptionMessageOnInvalidInput() {
        String message = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.InvalidOption, locale, serverLocale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(message));

    }
}
