package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.CharacterBuilder;
import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

@RunWith(MockitoJUnitRunner.class)
public class EnterSpaceTest {
    private final long locationId = RandomUtils.nextLong(100, 200);
    private final GameCharacter character = new CharacterBuilder().withLocationId(locationId).build();
    private final Connection connection = new Connection().withCharacter(character);

    @Mock Configuration configuration;
    @InjectMocks EnterSpace stage;

    @Test
    public void executeReturnsPlayCharacter() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayCharacter.class));
    }
//
//    @Test
//    public void executeDisplaySpaceTitleAndDescription() {
//        stage.execute(connection, configuration);
//
//        String expected = "";
//        assertThat(connection.getOutputQueue(), hasItem(expected));
//    }
}
