package org.mumue.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.space.SpaceBuilder;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;

public class EnterSpaceTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final Configuration configuration = mock(Configuration.class);
    private final SpaceDao spaceDao = mock(SpaceDao.class);
    private final EnterSpace stage = new EnterSpace(injector, spaceDao);

    private final long locationId = RandomUtils.nextLong(100, 200);
    private final GameCharacter character = new CharacterBuilder().withLocationId(locationId).build();
    private final Connection connection = new Connection(configuration).withCharacter(character);
    private final String name = RandomStringUtils.randomAlphabetic(25);
    private final String description = RandomStringUtils.randomAlphabetic(35);
    private final Space space = new SpaceBuilder().withName(name).withDescription(description).build();

    @Before
    public void beforeEach() {
        when(spaceDao.getSpace(locationId)).thenReturn(space);
    }

    @Test
    public void executeReturnsPlayCharacter() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayCharacter.class));
    }

    @Test
    public void executeDisplaySpaceTitleAndDescription() {
        stage.execute(connection, configuration);

        String expected = name + "\\r\\n" + description + "\\r\\n";
        assertThat(connection.getOutputQueue(), hasItem(expected));
    }
}
