package org.mumue.mumue.interpreter.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

public class CommandPoseTest {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final GameCharacter poser = new CharacterBuilder().withLocationId(RandomUtils.nextLong(100, 200)).build();
    private final Connection posingConnection = TestObjectBuilder.connection().withCharacter(poser);

    private final CommandPose commandPose = new CommandPose();

    @Before
    public void beforeEach() {
        connectionManager.add(posingConnection);
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
        Connection inRoomConnection = TestObjectBuilder.connection().withCharacter(new CharacterBuilder().withLocationId(poser.getLocationId()).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + " " + message + "\\r\\n";
        connectionManager.add(inRoomConnection);

        commandPose.execute(posingConnection, ":", message, configuration);

        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseNotSeenByCharacterInOtherLocation() {
        Connection otherRoomConnection = TestObjectBuilder.connection().withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(500, 600)).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String message = RandomStringUtils.randomAlphabetic(35);
        connectionManager.add(otherRoomConnection);

        commandPose.execute(posingConnection, ":", message, configuration);

        assertThat(otherRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
