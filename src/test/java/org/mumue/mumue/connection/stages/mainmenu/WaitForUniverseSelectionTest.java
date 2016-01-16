package org.mumue.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForUniverseSelectionTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final TextMaker textMaker = mock(TextMaker.class);
    private final UniverseDao dao = mock(UniverseDao.class);
    private final WaitForUniverseSelection stage = new WaitForUniverseSelection(injector, textMaker, dao);

    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
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
