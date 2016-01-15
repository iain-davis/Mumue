package org.mumue.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
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

public class UniverseSelectionPromptTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock UniverseDao universeDao;
    @InjectMocks UniverseSelectionPrompt stage;

    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.UniverseSelectionPrompt), eq(locale))).thenReturn(prompt);
    }

    @Test
    public void neverReturnNull() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void nextStageIsWaitForUniverseSelection() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForUniverseSelection.class));
    }

    @Test
    public void withOneUniverseDisplayUniverseList() {
        Collection<Universe> universes = new ArrayList<>();
        Universe universe = new Universe();
        universe.setId(0L);
        universe.setName(RandomStringUtils.randomAlphabetic(17));
        universes.add(universe);
        when(universeDao.getUniverses()).thenReturn(universes);
        stage.execute(connection, configuration);

        String expected = "\\r\\n" + universe.getId() + ") " + universe.getName() + "\\r\\n";

        assertThat(connection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void promptForUniverseSelection() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }
}
