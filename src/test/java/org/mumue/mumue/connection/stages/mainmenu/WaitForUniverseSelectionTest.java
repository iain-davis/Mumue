package org.mumue.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
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
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForUniverseSelectionTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock UniverseDao dao;
    @InjectMocks WaitForUniverseSelection stage;

    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);

    @Before
    public void beforeEach() {
        Universe universe = new Universe();
        universe.setId(RandomUtils.nextLong(100, 200));
        when(dao.getUniverse(anyLong())).thenReturn(universe);
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
    public void nextStageOnValidSelection() {
        connection.getInputQueue().push(RandomStringUtils.randomNumeric(2));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    public void setUniverseIdOnCharacter() {
        String selection = RandomStringUtils.randomNumeric(2);
        connection.getInputQueue().push(selection);

        stage.execute(connection, configuration);

        assertThat(connection.getCharacter().getUniverseId(), equalTo(Long.parseLong(selection)));
    }

    @Test
    public void rePromptOnInvalidSelection() {
        connection.getInputQueue().push(RandomStringUtils.randomNumeric(2));
        when(dao.getUniverse(anyLong())).thenReturn(new Universe());
        when(textMaker.getText(TextName.InvalidOption, locale)).thenReturn("");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void displayUnknownUniverseOnInvalidSelection() {
        String message = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.randomNumeric(2));
        when(dao.getUniverse(anyLong())).thenReturn(new Universe());
        when(textMaker.getText(TextName.InvalidOption, locale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
