package org.mumue.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForPlayerMenuChoiceTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Configuration configuration = mock(Configuration.class);
    private final CharacterDao characterDao = mock(CharacterDao.class);

    private final WaitForPlayerMenuChoice stage = new WaitForPlayerMenuChoice(injector, characterDao, textMaker);

    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final long id = RandomUtils.nextLong(100, 200);
    private final Player player = new PlayerBuilder().withId(id).withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);

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
        when(textMaker.getText(TextName.CharacterNeeded, locale)).thenReturn(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void playerSelectsPWithoutCharactersPutCharacterNeededMessageOnQueue() {
        String message = RandomStringUtils.randomAlphabetic(17);
        when(textMaker.getText(TextName.CharacterNeeded, locale)).thenReturn(message);
        connection.getInputQueue().push("P");

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));

    }

    @Test
    public void playerHasCharactersAndSelectsPGoToCharacterSelectionPrompt() {
        List<GameCharacter> characters = new ArrayList<>();
        characters.add(new GameCharacter());
        when(characterDao.getCharacters(player.getId())).thenReturn(characters);
        connection.getInputQueue().push("P");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    public void redisplayMenuOnInvalidInput() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(textMaker.getText(TextName.InvalidOption, locale)).thenReturn("");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void displayInvalidOptionMessageOnInvalidInput() {
        String message = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.InvalidOption, locale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(message));

    }
}
