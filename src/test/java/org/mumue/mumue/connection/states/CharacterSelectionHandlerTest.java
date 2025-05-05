package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CharacterSelectionHandlerTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final String message = RandomStringUtils.insecure().nextAlphabetic(17);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final Connection connection = new Connection(configuration).withPlayer(Nimue.player().build());
    private final CharacterSelectionHandler characterSelectionHandler = new CharacterSelectionHandler(connectionStateProvider, characterDao, textMaker);

    @BeforeEach
    void beforeEach() {
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);
    }

    @Test
    void returnWaitForSelectionWithoutInput() {
        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, sameInstance(characterSelectionHandler));
    }

    @Test
    void returnPlayCharacterWithInput() {
        String text = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(text);
        connection.getMenuOptionIds().put(text, RandomUtils.insecure().randomLong(100, 200));
        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(EnterUniverse.class));
    }

    @Test
    void loadSelectedCharacter() {
        long characterId = RandomUtils.insecure().randomLong(100, 200);
        String option = RandomStringUtils.insecure().nextAlphabetic(1);
        connection.getMenuOptionIds().put(option, characterId);
        connection.getInputQueue().push(option);

        characterSelectionHandler.execute(connection, configuration);

        verify(characterDao).getCharacter(characterId);
    }

    @Test
    void rePromptOnInvalidSelection() {
        long characterId = RandomUtils.insecure().randomLong(100, 200);
        String option = RandomStringUtils.insecure().nextAlphabetic(1);
        String badSelection = RandomStringUtils.insecure().nextAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    void displayInvalidSelectionOnBadSelection() {
        long characterId = RandomUtils.insecure().randomLong(100, 200);
        String option = RandomStringUtils.insecure().nextAlphabetic(1);
        String badSelection = RandomStringUtils.insecure().nextAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        characterSelectionHandler.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
