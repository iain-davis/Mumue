package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.space.SpaceBuilder;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;

public class EnterSpaceTest {
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final SpaceDao spaceDao = mock(SpaceDao.class);
    private final StateCollection stateCollection = mock(StateCollection.class);

    private final long locationId = RandomUtils.nextLong(100, 200);
    private final GameCharacter character = new CharacterBuilder().withLocationId(locationId).build();
    private final Connection connection = new Connection(configuration).withCharacter(character);
    private final String name = RandomStringUtils.randomAlphabetic(25);
    private final String description = RandomStringUtils.randomAlphabetic(35);
    private final Space space = new SpaceBuilder().withName(name).withDescription(description).build();

    private final EnterSpace enterSpace = new EnterSpace(stateCollection, spaceDao);

    @Before
    public void beforeEach() {
        when(spaceDao.getSpace(locationId)).thenReturn(space);
        when(stateCollection.get(StateName.PlayCharacter)).thenReturn(new PlayCharacter(null, null));
    }

    @Test
    public void executeReturnsPlayCharacter() {
        ConnectionState next = enterSpace.execute(connection, configuration);

        assertThat(next, instanceOf(PlayCharacter.class));
    }

    @Test
    public void executeDisplaySpaceTitleAndDescription() {
        enterSpace.execute(connection, configuration);

        String expected = name + GlobalConstants.TCP_LINE_SEPARATOR + description + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(connection.getOutputQueue(), hasItem(expected));
    }
}
