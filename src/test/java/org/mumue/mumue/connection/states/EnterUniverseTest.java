package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mockito.Matchers;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class EnterUniverseTest {
    private final long universeId = RandomUtils.nextLong(100, 200);
    private final String message = RandomStringUtils.randomAlphabetic(25);
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final UniverseDao dao = mock(UniverseDao.class);
    private final String universeName = RandomStringUtils.randomAlphabetic(17);
    private final Universe universe = new UniverseBuilder().withName(universeName).withId(universeId).build();
    private final GameCharacter character = TestObjectBuilder.character().withUniverseId(universeId).build();
    private final Player player = TestObjectBuilder.player().build();
    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);
    private final ConnectionStateProvider connectionStateProvider = TestObjectBuilder.stateService();
    private final EnterUniverse enterUniverse = new EnterUniverse(connectionStateProvider, textMaker, dao);


    @Test
    public void executeReturnsEnterSpace() {
        when(textMaker.getText(Matchers.eq(TextName.EnterUniverse), eq(ConfigurationDefaults.SERVER_LOCALE), any())).thenReturn(message);
        when(dao.getUniverse(universeId)).thenReturn(universe);

        ConnectionState next = enterUniverse.execute(connection, configuration);

        assertThat(next, instanceOf(EnterSpace.class));
    }

    @Test
    public void enterUniverseDisplayEntryMessage() {
        when(textMaker.getText(Matchers.eq(TextName.EnterUniverse), eq(ConfigurationDefaults.SERVER_LOCALE), any())).thenReturn(message);
        when(dao.getUniverse(universeId)).thenReturn(universe);

        enterUniverse.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
