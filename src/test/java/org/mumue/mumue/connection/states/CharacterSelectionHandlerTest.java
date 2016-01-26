package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterSelectionHandlerTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final CharacterDao characterDao = mock(CharacterDao.class);
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final String message = RandomStringUtils.randomAlphabetic(17);
    private final ConnectionStateService connectionStateService = TestObjectBuilder.stateService();
    private final Connection connection = new Connection(configuration).withPlayer(TestObjectBuilder.player().build());
    private final CharacterSelectionHandler characterSelectionHandler = new CharacterSelectionHandler(connectionStateService, characterDao, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);
    }

    @Test
    public void returnWaitForSelectionWithoutInput() {
        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, sameInstance(characterSelectionHandler));
    }

    @Test
    public void returnPlayCharacterWithInput() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);
        connection.getMenuOptionIds().put(text, RandomUtils.nextLong(100, 200));
        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(EnterUniverse.class));
    }

    @Test
    public void loadSelectedCharacter() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        connection.getMenuOptionIds().put(option, characterId);
        connection.getInputQueue().push(option);

        characterSelectionHandler.execute(connection, configuration);

        verify(characterDao).getCharacter(characterId);
    }

    @Test
    public void rePromptOnInvalidSelection() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        String badSelection = RandomStringUtils.randomAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        ConnectionState next = characterSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    public void displayInvalidSelectionOnBadSelection() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        String badSelection = RandomStringUtils.randomAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        characterSelectionHandler.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
