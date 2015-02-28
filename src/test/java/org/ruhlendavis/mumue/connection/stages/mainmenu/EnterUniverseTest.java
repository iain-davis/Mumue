package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.CharacterBuilder;
import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.components.Universe;
import org.ruhlendavis.mumue.components.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class EnterUniverseTest {
    private final String message = RandomStringUtils.randomAlphabetic(25);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private long universeId = RandomUtils.nextLong(100, 200);
    private final String universeName = RandomStringUtils.randomAlphabetic(17);
    private final Universe universe = new Universe().withName(universeName).withId(universeId);
    private final GameCharacter character = new CharacterBuilder().withUniverseId(universeId).build();

    private final Player player = new PlayerBuilder().withLocale(locale).build();

    private final Connection connection = new Connection().withPlayer(player).withCharacter(character);
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock UniverseDao dao;
    @InjectMocks EnterUniverse stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(eq(TextName.EnterUniverse), eq(locale), eq(serverLocale), any(Map.class))).thenReturn(message);
        when(dao.getUniverse(universeId)).thenReturn(universe);
    }

    @Test
    public void executeReturnsEnterSpace() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(EnterSpace.class));
    }

    @Test
    public void enterUniverseDisplayEntryMessage() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
