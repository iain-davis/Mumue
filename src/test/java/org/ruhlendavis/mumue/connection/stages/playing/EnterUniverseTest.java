package org.ruhlendavis.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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

import org.ruhlendavis.mumue.components.character.CharacterBuilder;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.components.universe.Universe;
import org.ruhlendavis.mumue.components.universe.UniverseBuilder;
import org.ruhlendavis.mumue.components.universe.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class EnterUniverseTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @Mock UniverseDao dao;
    @InjectMocks EnterUniverse stage;

    private final String message = RandomStringUtils.randomAlphabetic(25);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private long universeId = RandomUtils.nextLong(100, 200);
    private final String universeName = RandomStringUtils.randomAlphabetic(17);
    private final Universe universe = new UniverseBuilder().withName(universeName).withId(universeId).build();
    private final GameCharacter character = new CharacterBuilder().withUniverseId(universeId).build();

    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.EnterUniverse), eq(locale), any())).thenReturn(message);
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
