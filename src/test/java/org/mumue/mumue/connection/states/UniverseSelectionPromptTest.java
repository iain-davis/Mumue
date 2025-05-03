package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class UniverseSelectionPromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final Player player = Nimue.player().build();
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final UniverseSelectionPrompt universeSelectionPrompt = new UniverseSelectionPrompt(connectionStateProvider, universeRepository, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.UniverseSelectionPrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(prompt);
    }

    @Test
    public void neverReturnNull() {
        ConnectionState next = universeSelectionPrompt.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void nextStageIsWaitForUniverseSelection() {
        ConnectionState next = universeSelectionPrompt.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionHandler.class));
    }

    @Test
    public void withOneUniverseDisplayUniverseList() {
        Collection<Universe> universes = new ArrayList<>();
        Universe universe = new Universe();
        universe.setId(0L);
        universe.setName(RandomStringUtils.insecure().nextAlphabetic(17));
        universes.add(universe);
        when(universeRepository.getUniverses()).thenReturn(universes);
        universeSelectionPrompt.execute(connection, configuration);

        String expected = GlobalConstants.TCP_LINE_SEPARATOR + universe.getId() + ") " + universe.getName() + GlobalConstants.TCP_LINE_SEPARATOR;

        assertThat(connection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void promptForUniverseSelection() {
        universeSelectionPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }
}
