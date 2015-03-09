package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
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

import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.playing.EnterUniverse;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterSelectionTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock CharacterDao dao;
    @InjectMocks WaitForCharacterSelection stage;

    private final String message = RandomStringUtils.randomAlphabetic(17);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final Connection connection = new Connection(configuration).withPlayer(new PlayerBuilder().withLocale(locale).build());

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.InvalidOption, locale, serverLocale)).thenReturn(message);
    }

    @Test
    public void returnWaitForSelectionWithoutInput() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void returnPlayCharacterWithInput() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);
        connection.getMenuOptionIds().put(text, RandomUtils.nextLong(100, 200));
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(EnterUniverse.class));
    }

    @Test
    public void loadSelectedCharacter() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        connection.getMenuOptionIds().put(option, characterId);
        connection.getInputQueue().push(option);

        stage.execute(connection, configuration);

        verify(dao).getCharacter(characterId);
    }

    @Test
    public void rePromptOnInvalidSelection() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        String badSelection = RandomStringUtils.randomAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterSelectionPrompt.class));
    }

    @Test
    public void displayInvalidSelectionOnBadSelection() {
        long characterId = RandomUtils.nextLong(100, 200);
        String option = RandomStringUtils.randomAlphabetic(1);
        String badSelection = RandomStringUtils.randomAlphabetic(2);
        connection.getInputQueue().push(badSelection);
        connection.getMenuOptionIds().put(option, characterId);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
