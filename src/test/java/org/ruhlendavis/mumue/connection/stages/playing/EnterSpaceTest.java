package org.ruhlendavis.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.character.CharacterBuilder;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.components.space.Space;
import org.ruhlendavis.mumue.components.space.SpaceBuilder;
import org.ruhlendavis.mumue.components.space.SpaceDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

@RunWith(MockitoJUnitRunner.class)
public class EnterSpaceTest {
    private final long locationId = RandomUtils.nextLong(100, 200);
    private final GameCharacter character = new CharacterBuilder().withLocationId(locationId).build();
    private final Connection connection = new Connection().withCharacter(character);
    private final String name = RandomStringUtils.randomAlphabetic(25);
    private final String description = RandomStringUtils.randomAlphabetic(35);
    private final Space space = new SpaceBuilder().withName(name).withDescription(description).build();

    @Mock Configuration configuration;
    @Mock SpaceDao spaceDao;
    @InjectMocks EnterSpace stage;

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
