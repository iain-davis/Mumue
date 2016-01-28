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
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class CommandSayTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final GameCharacter sayer = new CharacterBuilder().withLocationId(RandomUtils.nextLong(100, 200)).build();
    private final Connection sayerConnection = Nimue.connection().withCharacter(sayer);

    private final CommandSay command = new CommandSay();

    @Before
    public void beforeEach() {
        connectionManager.add(sayerConnection);
    }

    @Test
    public void youSay() {
        String saying = RandomStringUtils.randomAlphabetic(17);
        command.execute(sayerConnection, "", saying, configuration);

        String expected = "You say, \"" + saying + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(sayerConnection.getOutputQueue().size(), equalTo(1));
        assertThat(sayerConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void otherCharacterInRoomSees() {
        long locationId = RandomUtils.nextLong(100, 200);
        sayer.setLocationId(locationId);
        sayer.setName(RandomStringUtils.randomAlphabetic(16));
        Connection otherConnection = new Connection(configuration).withCharacter(new CharacterBuilder().withLocationId(locationId).build());
        String saying = RandomStringUtils.randomAlphabetic(17);
        connectionManager.add(otherConnection);

        command.execute(sayerConnection, "", saying, configuration);

        String expected = sayer.getName() + " says, \"" + saying + "\"" + GlobalConstants.TCP_LINE_SEPARATOR;
        assertThat(otherConnection.getOutputQueue().size(), equalTo(1));
        assertThat(otherConnection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void otherCharacterInOtherRoomDoesNotSee() {
        long locationId = RandomUtils.nextLong(100, 200);
        sayer.setLocationId(locationId);
        sayer.setName(RandomStringUtils.randomAlphabetic(16));
        String saying = RandomStringUtils.randomAlphabetic(17);

        Connection inRoomConnection = new Connection(configuration).withCharacter(new CharacterBuilder().withLocationId(locationId).build());
        Connection outOfRoomConnection = new Connection(configuration).withCharacter(new CharacterBuilder().withLocationId(RandomUtils.nextLong(200, 300)).build());
        connectionManager.add(inRoomConnection);
        connectionManager.add(outOfRoomConnection);

        command.execute(sayerConnection, "", saying, configuration);

        assertThat(outOfRoomConnection.getOutputQueue().size(), equalTo(0));
    }
}
