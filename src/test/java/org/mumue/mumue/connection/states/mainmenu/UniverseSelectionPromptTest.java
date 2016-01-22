package org.mumue.mumue.connection.states.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class UniverseSelectionPromptTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final TextMaker textMaker = mock(TextMaker.class);
    private final UniverseDao universeDao = mock(UniverseDao.class);
    private final UniverseSelectionPrompt stage = new UniverseSelectionPrompt(injector, universeDao, textMaker);

    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new PlayerBuilder().withLocale(locale).build();

    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration).withPlayer(player);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.UniverseSelectionPrompt), eq(locale))).thenReturn(prompt);
    }

    @Test
    public void neverReturnNull() {
        ConnectionState next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void nextStageIsWaitForUniverseSelection() {
        ConnectionState next = stage.execute(connection, configuration);

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

        String expected = GlobalConstants.TCP_LINE_SEPARATOR + universe.getId() + ") " + universe.getName() + GlobalConstants.TCP_LINE_SEPARATOR;

        assertThat(connection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void promptForUniverseSelection() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }
}
