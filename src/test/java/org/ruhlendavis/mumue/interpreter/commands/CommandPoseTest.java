package org.ruhlendavis.mumue.interpreter.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Vector;

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
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.ConnectionManager;

public class CommandPoseTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock ConnectionManager connectionManager;
    @Mock Configuration configuration;
    @InjectMocks CommandPose commandPose;
    private final GameCharacter poser = new CharacterBuilder().withLocationId(RandomUtils.nextLong(100, 200)).build();
    private final Connection posingConnection = new Connection().withCharacter(poser);
    private final Vector<Connection> connections = new Vector<>();

    @Before
    public void beforeEach() {
        connections.add(posingConnection);
        when(connectionManager.getConnections()).thenReturn(connections);
    }

    @Test
    public void poseSeenByPoserWithSpace() {
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + " " + message + "\\r\\n";

        commandPose.execute(posingConnection, ":", message, configuration);

        assertThat(posingConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseSeenByPoserWithoutSpace() {
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + message + "\\r\\n";

        commandPose.execute(posingConnection, ";", message, configuration);

        assertThat(posingConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseSeenByOtherCharacterInRoom() {
        Connection inRoomConnection = new Connection().withCharacter(new CharacterBuilder().withLocationId(poser.getLocationId()).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + " " + message + "\\r\\n";
        connections.add(inRoomConnection);

        commandPose.execute(posingConnection, ":", message, configuration);

        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseNotSeenByCharacterInOtherRoom() {
        Connection otherRoomConnection = new Connection().withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(500, 600)).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        connections.add(otherRoomConnection);

        commandPose.execute(posingConnection, ":", message, configuration);

        assertThat(otherRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
