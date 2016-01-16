package org.mumue.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
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

public class EnterUniverseTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);

    private final UniverseDao dao = mock(UniverseDao.class);
    private final EnterUniverse stage = new EnterUniverse(injector, textMaker, dao);

    private final String message = RandomStringUtils.randomAlphabetic(25);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final long universeId = RandomUtils.nextLong(100, 200);
    private final String universeName = RandomStringUtils.randomAlphabetic(17);
    private final Universe universe = new UniverseBuilder().withName(universeName).withId(universeId).build();
    private final GameCharacter character = new CharacterBuilder().withUniverseId(universeId).build();

    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player).withCharacter(character);

    @Before
    public void beforeEach() {
        when(textMaker.getText(Matchers.eq(TextName.EnterUniverse), eq(locale), any())).thenReturn(message);
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
