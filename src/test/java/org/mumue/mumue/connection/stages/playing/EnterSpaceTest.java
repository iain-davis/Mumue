package org.mumue.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
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

import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.SpaceBuilder;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class EnterSpaceTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock SpaceDao spaceDao;
    @InjectMocks EnterSpace stage;

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
