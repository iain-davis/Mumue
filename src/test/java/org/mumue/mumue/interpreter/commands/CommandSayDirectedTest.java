package org.mumue.mumue.interpreter.commands;

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
import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandSayDirectedTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock TextMaker textMaker;
    @Mock ConnectionManager connectionManager;
    @Mock
    ApplicationConfiguration configuration;
    @InjectMocks CommandSayDirected command;

    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final Vector<Connection> connections = new Vector<>();
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final GameCharacter sayer = new CharacterBuilder().withName(RandomStringUtils.randomAlphabetic(7))
            .withLocationId(RandomUtils.nextLong(100, 200)).build();
    private final GameCharacter target = new CharacterBuilder().withName(RandomStringUtils.randomAlphabetic(8).toUpperCase())
            .withLocationId(sayer.getLocationId()).build();
    private final Connection sayerConnection = new Connection(configuration).withPlayer(player).withCharacter(sayer);
    private final Connection targetConnection = new Connection(configuration).withCharacter(target);

    @Before
    public void beforeEach() {
        connections.add(sayerConnection);
        connections.add(targetConnection);
        when(connectionManager.getConnections()).thenReturn(connections);
    }

    @Test
    public void messageSeenBySayer() {
        String message = RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName() + " " + message, configuration);
        String expected = sayer.getName() + " says to " + target.getName() + ", \"" + message + "\"\\r\\n";
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetMatchPartial() {
        when(textMaker.getText(TextName.TargetBeingNotFound, locale)).thenReturn("");
        String message = RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName().substring(0, RandomUtils.nextInt(3, 6)) + " " + message, configuration);
        String expected = sayer.getName() + " says to " + target.getName() + ", \"" + message + "\"\\r\\n";
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetMatchCaseInsensitive() {
        when(textMaker.getText(TextName.TargetBeingNotFound, locale)).thenReturn("");
        String message = RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName().toLowerCase() + " " + message, configuration);
        String expected = sayer.getName() + " says to " + target.getName() + ", \"" + message + "\"\\r\\n";
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void targetNotFound() {
        String responseMessage = RandomStringUtils.randomAlphabetic(18);
        String message = RandomStringUtils.randomAlphabetic(17);
        String otherTarget = RandomStringUtils.randomAlphabetic(8);

        when(textMaker.getText(TextName.TargetBeingNotFound, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", otherTarget + " " + message, configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void messageWithSpace() {
        String message = RandomStringUtils.randomAlphabetic(17) + " " + RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName() + " " + message, configuration);

        String expected = sayer.getName() + " says to " + target.getName() + ", \"" + message + "\"\\r\\n";
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void noMessage() {
        String responseMessage = RandomStringUtils.randomAlphabetic(18);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", target.getName(), configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void emptyMessage() {
        String responseMessage = RandomStringUtils.randomAlphabetic(18);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", target.getName() + " ", configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void messageOnlySpaces() {
        String responseMessage = RandomStringUtils.randomAlphabetic(18);

        when(textMaker.getText(TextName.MissingSayText, locale)).thenReturn(responseMessage);

        command.execute(sayerConnection, "`", target.getName() + "       ", configuration);

        assertThat(sayerConnection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void messageSeenByInSpaceCharacter() {
        Connection inRoomConnection = new Connection(configuration).withCharacter(new CharacterBuilder().withLocationId(sayer.getLocationId()).build());
        connections.add(inRoomConnection);

        String message = RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName() + " " + message, configuration);
        String expected = sayer.getName() + " says to " + target.getName() + ", \"" + message + "\"\\r\\n";
        assertThat(inRoomConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void messageNotSeenByOutOfSpaceCharacter() {
        Connection outOfRoomConnection = new Connection(configuration)
                .withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(500, 600)).build());
        connections.add(outOfRoomConnection);

        String message = RandomStringUtils.randomAlphabetic(17);

        command.execute(sayerConnection, "`", target.getName() + " " + message, configuration);

        assertThat(outOfRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
