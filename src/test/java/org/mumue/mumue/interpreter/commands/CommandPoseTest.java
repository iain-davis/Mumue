package org.mumue.mumue.interpreter.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

@RunWith(Theories.class)
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
        String text = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + " " + text + "\\r\\n";

        commandPose.execute(posingConnection, ":", text, configuration);

        System.out.println(posingConnection.getOutputQueue());
        assertThat(posingConnection.getOutputQueue(), hasItem(expected));
    }

    @Theory
    public void poseDoesNotAddSpaceForNonAlphabetic(char leadingCharacter) {
        assumeThat(Character.isAlphabetic(leadingCharacter), is(false));
        String text = leadingCharacter + RandomStringUtils.randomAlphabetic(25);
        commandPose.execute(posingConnection, ":", text, configuration);
        String expected = poser.getName() + text + GlobalConstants.NEW_LINE;
        assertThat("" + leadingCharacter, posingConnection.getOutputQueue(), hasItem(expected));
    }

    @DataPoints
    public static char[] generatedDataPoints() {
        char values[] = new char[256];
        for (char character = 33; character < 126; character++) {
            values[character] = character;
        }
        return values;
    }

    @Test
    public void poseSeenByOtherCharacterInRoom() {
        Connection inRoomConnection = TestObjectBuilder.connection().withCharacter(new CharacterBuilder().withLocationId(poser.getLocationId()).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String text = RandomStringUtils.randomAlphabetic(35);
        String expected = poser.getName() + " " + text + "\\r\\n";
        connectionManager.add(inRoomConnection);

        commandPose.execute(posingConnection, ":", text, configuration);

        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseNotSeenByCharacterInOtherLocation() {
        Connection otherRoomConnection = TestObjectBuilder.connection().withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(500, 600)).build());
        poser.setName(RandomStringUtils.randomAlphabetic(17));
        String text = RandomStringUtils.randomAlphabetic(35);
        connectionManager.add(otherRoomConnection);

        commandPose.execute(posingConnection, ":", text, configuration);

        assertThat(otherRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
