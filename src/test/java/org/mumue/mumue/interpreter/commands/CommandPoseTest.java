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
import org.mumue.mumue.testobjectbuilder.Nimue;

@RunWith(Theories.class)
public class CommandPoseTest {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final GameCharacter poser = new CharacterBuilder().withLocationId(RandomUtils.insecure().randomLong(100, 200)).build();
    private final Connection posingConnection = Nimue.connection().withCharacter(poser);

    private final CommandPose commandPose = new CommandPose();

    @Before
    public void beforeEach() {
        connectionManager.add(posingConnection);
    }

    @Test
    public void poseSeenByPoserWithSpace() {
        poser.setName(RandomStringUtils.insecure().nextAlphabetic(17));
        String text = RandomStringUtils.insecure().nextAlphabetic(35);

        commandPose.execute(posingConnection, ":", text, configuration);

        String expected = poser.getName() + " " + text + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(posingConnection.getOutputQueue(), hasItem(expected));
    }

    @Theory
    public void poseDoesNotAddSpaceForNonAlphabetic(char leadingCharacter) {
        assumeThat(Character.isAlphabetic(leadingCharacter), is(false));
        String text = leadingCharacter + RandomStringUtils.insecure().nextAlphabetic(25);
        commandPose.execute(posingConnection, ":", text, configuration);
        String expected = poser.getName() + text + GlobalConstants.TCP_LINE_SEPARATOR;
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
        Connection inRoomConnection = Nimue.connection().withCharacter(new CharacterBuilder().withLocationId(poser.getLocationId()).build());
        poser.setName(RandomStringUtils.insecure().nextAlphabetic(17));
        String text = RandomStringUtils.insecure().nextAlphabetic(35);
        String expected = poser.getName() + " " + text + GlobalConstants.TCP_LINE_SEPARATOR;
        connectionManager.add(inRoomConnection);

        commandPose.execute(posingConnection, ":", text, configuration);

        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void poseNotSeenByCharacterInOtherLocation() {
        Connection otherRoomConnection = Nimue.connection().withCharacter(new CharacterBuilder().withLocationId(RandomUtils.insecure().randomLong(500, 600)).build());
        poser.setName(RandomStringUtils.insecure().nextAlphabetic(17));
        String text = RandomStringUtils.insecure().nextAlphabetic(35);
        connectionManager.add(otherRoomConnection);

        commandPose.execute(posingConnection, ":", text, configuration);

        assertThat(otherRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
