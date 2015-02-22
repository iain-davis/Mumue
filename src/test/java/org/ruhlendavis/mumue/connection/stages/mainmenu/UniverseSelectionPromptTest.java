package org.ruhlendavis.mumue.connection.stages.mainmenu;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.Universe;
import org.ruhlendavis.mumue.components.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class UniverseSelectionPromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);
    private final Player player = new Player().withLocale(locale);
    private final Connection connection = new Connection().withPlayer(player);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock UniverseDao universeDao;
    @InjectMocks UniverseSelectionPrompt stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(eq(TextName.UniverseSelectionPrompt), eq(locale), eq(serverLocale))).thenReturn(prompt);
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
