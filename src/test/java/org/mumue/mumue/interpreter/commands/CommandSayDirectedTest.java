package org.mumue.mumue.interpreter.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandSayDirectedTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final String locale = RandomStringUtils.insecure().nextAlphabetic(16);
    private final Player player = new PlayerBuilder().withLocale(locale).build();

    private final CommandSayDirected command = new CommandSayDirected(connectionManager, textMaker);

    @Test
    public void messageSeenBySayer() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);
        Connection sayerConnection = connection(null, character(characterName, locationId));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(null, character(targetName, locationId)));

        command.execute(sayerConnection, "`", targetName + " " + message, configuration);

        String expected = characterName + " says to " + targetName + ", \"" + message + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void messageWithSpace() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17) + " " + RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);
        Connection sayerConnection = connection(null, character(characterName, locationId));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(null, character(targetName, locationId)));

        command.execute(sayerConnection, "`", targetName + " " + message, configuration);

        String expected = characterName + " says to " + targetName + ", \"" + message + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetMatchPartial() {
        String locale = RandomStringUtils.insecure().nextAlphabetic(16);
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);

        Player targetPlayer = new PlayerBuilder().withLocale(locale).build();
        Connection sayerConnection = connection(null, character(characterName, locationId));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(targetPlayer, character(targetName, locationId)));

        when(textMaker.getText(TextName.TargetBeingNotFound, locale)).thenReturn("");

        command.execute(sayerConnection, "`", targetName.substring(0, RandomUtils.nextInt(3, 6)) + " " + message, configuration);

        String expected = characterName + " says to " + targetName + ", \"" + message + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetMatchCaseInsensitive() {
        String locale = RandomStringUtils.insecure().nextAlphabetic(16);
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);

        Player targetPlayer = new PlayerBuilder().withLocale(locale).build();
        Connection sayerConnection = connection(null, character(characterName, locationId));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(targetPlayer, character(targetName, locationId)));

        when(textMaker.getText(TextName.TargetBeingNotFound, locale)).thenReturn("");

        command.execute(sayerConnection, "`", targetName.toLowerCase() + " " + message, configuration);

        String expected = characterName + " says to " + targetName + ", \"" + message + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetNotFound() {
        String responseMessage = RandomStringUtils.insecure().nextAlphabetic(18);
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String otherTarget = RandomStringUtils.insecure().nextAlphabetic(8);
        Player player = new PlayerBuilder().withLocale(locale).build();

        Connection sayerConnection = connection(player, character("", 0));
        connectionManager.add(sayerConnection);

        when(textMaker.getText(eq(TextName.TargetBeingNotFound), eq(locale), anyMap())).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", otherTarget + " " + message, configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void noMessage() {
        String responseMessage = RandomStringUtils.insecure().nextAlphabetic(18);
        Connection sayerConnection = connection(player, character("", 0));
        connectionManager.add(sayerConnection);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", "", configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void emptyMessage() {
        String responseMessage = RandomStringUtils.insecure().nextAlphabetic(18);
        Connection sayerConnection = connection(player, character("", 0));
        connectionManager.add(sayerConnection);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", "", configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    private Connection connection(Player player, GameCharacter character) {
        return Nimue.connection().withPlayer(player).withCharacter(character);
    }

    private GameCharacter character(String name, long locationId) {
        return new CharacterBuilder().withName(name).withLocationId(locationId).build();
    }

    @Test
    public void messageOnlySpaces() {
        String responseMessage = RandomStringUtils.insecure().nextAlphabetic(18);
        Connection sayerConnection = connection(player, character("", 0));
        connectionManager.add(sayerConnection);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", RandomStringUtils.insecure().nextAlphabetic(7) + "       ", configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void messageSeenByInSpaceCharacter() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);

        Connection sayerConnection = connection(null, character(characterName, locationId));
        Connection inRoomConnection = Nimue.connection().withCharacter(character("", locationId));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(null, character(targetName, locationId)));
        connectionManager.add(inRoomConnection);

        command.execute(sayerConnection, "`", targetName + " " + message, configuration);

        String expected = characterName + " says to " + targetName + ", \"" + message + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void messageNotSeenByOutOfSpaceCharacter() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String characterName = RandomStringUtils.insecure().nextAlphabetic(7);
        String targetName = RandomStringUtils.insecure().nextAlphabetic(8);
        long locationId = RandomUtils.insecure().randomLong(100, 200);

        Connection sayerConnection = connection(null, character(characterName, locationId));
        Connection outOfRoomConnection = Nimue.connection().withCharacter(character("", RandomUtils.insecure().randomLong(500, 600)));
        connectionManager.add(sayerConnection);
        connectionManager.add(connection(null, character(targetName, locationId)));
        connectionManager.add(outOfRoomConnection);

        command.execute(sayerConnection, "`", targetName + " " + message, configuration);

        assertThat(outOfRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
