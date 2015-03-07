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

public class CommandSayTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock ConnectionManager connectionManager;
    @InjectMocks CommandSay command;
    private final Vector<Connection> connections = new Vector<>();
    private final GameCharacter sayer = new CharacterBuilder().withLocationId(RandomUtils.nextLong(100, 200)).build();
    private final Connection sayerConnection = new Connection().withCharacter(sayer);

    @Before
    public void beforeEach() {
        connections.add(sayerConnection);
        when(connectionManager.getConnections()).thenReturn(connections);
    }

    @Test
    public void youSay() {
        String saying = RandomStringUtils.randomAlphabetic(17);
        command.execute(sayerConnection, "", saying, configuration);

        String expected = "You say, \"" + saying + "\"\\r\\n";
        assertThat(sayerConnection.getOutputQueue().size(), equalTo(1));
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void otherCharacterInRoomSees() {
        long locationId = RandomUtils.nextLong(100, 200);
        sayer.setLocationId(locationId);
        sayer.setName(RandomStringUtils.randomAlphabetic(16));
        Connection otherConnection = new Connection().withCharacter(new CharacterBuilder().withLocationId(locationId).build());
        String saying = RandomStringUtils.randomAlphabetic(17);
        connections.add(otherConnection);

        command.execute(sayerConnection, "", saying, configuration);

        String expected = sayer.getName() + " says, \"" + saying + "\"\\r\\n";
        assertThat(otherConnection.getOutputQueue().size(), equalTo(1));
        assertThat(otherConnection.getOutputQueue(), hasItem(expected));

    }

    @Test
    public void otherCharacterInOtherRoomDoesNotSee() {
        long locationId = RandomUtils.nextLong(100, 200);
        sayer.setLocationId(locationId);
        sayer.setName(RandomStringUtils.randomAlphabetic(16));
        String saying = RandomStringUtils.randomAlphabetic(17);

        Connection inRoomConnection = new Connection().withCharacter(new CharacterBuilder().withLocationId(locationId).build());
        Connection outOfRoomConnection = new Connection().withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(200, 300)).build());
        connections.add(inRoomConnection);
        connections.add(outOfRoomConnection);

        command.execute(sayerConnection, "", saying, configuration);

        assertThat(outOfRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
