package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerMenuHandlerTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final CharacterDao characterDao = mock(CharacterDao.class);

    private final long id = RandomUtils.insecure().randomLong(100, 200);
    private final Player player = Nimue.player().withId(id).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final PlayerMenuHandler playerMenuHandler = new PlayerMenuHandler(connectionStateProvider, characterDao, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(RandomStringUtils.insecure().nextAlphabetic(25));
    }

    @Test
    public void neverReturnNull() {
        assertNotNull(playerMenuHandler.execute(connection, configuration));
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, sameInstance(playerMenuHandler));
    }

    @Test
    public void goToUniverseSelectionOnCInput() {
        connection.getInputQueue().push("C");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void acceptLowerCase() {
        connection.getInputQueue().push("c");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void playerSelectsPWithoutCharactersGoToUniverseSelectionPrompt() {
        connection.getInputQueue().push("P");
        when(textMaker.getText(TextName.CharacterNeeded, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(RandomStringUtils.insecure().nextAlphabetic(17));

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void playerSelectsPWithoutCharactersPutCharacterNeededMessageOnQueue() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        when(textMaker.getText(TextName.CharacterNeeded, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);
        connection.getInputQueue().push("P");

        playerMenuHandler.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));

    }

    @Test
    public void playerHasCharactersAndSelectsPGoToCharacterSelectionPrompt() {
        List<GameCharacter> characters = new ArrayList<>();
        characters.add(new GameCharacter());
        when(characterDao.getCharacters(player.getId())).thenReturn(characters);
        connection.getInputQueue().push("P");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    public void goToAdministratorMenu() {
        Player administrator = Nimue.player().withAdministrator().build();
        connection.setPlayer(administrator);
        connection.getInputQueue().push("A");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(AdministrationMenu.class));
    }

    @Test
    public void ignoreAdministratorMenuWhenPlayerIsNotAdministrator() {
        Player administrator = Nimue.player().withoutAdministrator().build();
        connection.setPlayer(administrator);
        connection.getInputQueue().push("A");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }


    @Test
    public void redisplayMenuOnInvalidInput() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(3));
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState next = playerMenuHandler.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void displayInvalidOptionMessageOnInvalidInput() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(3));
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        playerMenuHandler.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(message));
    }
}
