package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class UniverseSelectionHandlerTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final UniverseRepository dao = mock(UniverseRepository.class);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final Player player = new PlayerBuilder().build();
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final UniverseSelectionHandler universeSelectionHandler = new UniverseSelectionHandler(connectionStateProvider, textMaker, dao);

    @Before
    public void beforeEach() {
        Universe universe = new Universe();
        universe.setId(RandomUtils.insecure().randomLong(100, 200));
        when(dao.getUniverse(anyLong())).thenReturn(universe);
    }

    @Test
    public void neverReturnNull() {
        ConnectionState next = universeSelectionHandler.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionState next = universeSelectionHandler.execute(connection, configuration);

        assertThat(next, sameInstance(universeSelectionHandler));
    }

    @Test
    public void nextStageOnValidSelection() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextNumeric(2));

        ConnectionState next = universeSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNamePrompt.class));
    }

    @Test
    public void setUniverseIdOnCharacter() {
        String selection = RandomStringUtils.insecure().nextNumeric(2);
        connection.getInputQueue().push(selection);

        universeSelectionHandler.execute(connection, configuration);

        assertThat(connection.getCharacter().getUniverseId(), equalTo(Long.parseLong(selection)));
    }

    @Test
    public void rePromptOnInvalidSelection() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextNumeric(2));
        when(dao.getUniverse(anyLong())).thenReturn(new Universe());
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState next = universeSelectionHandler.execute(connection, configuration);

        assertThat(next, instanceOf(UniverseSelectionPrompt.class));
    }

    @Test
    public void displayUnknownUniverseOnInvalidSelection() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.insecure().nextNumeric(2));
        when(dao.getUniverse(anyLong())).thenReturn(new Universe());
        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(message);

        universeSelectionHandler.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
