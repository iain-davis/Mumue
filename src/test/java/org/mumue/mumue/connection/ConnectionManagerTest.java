package org.mumue.mumue.connection;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

public class ConnectionManagerTest {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Before
    public void beforeEach() {
        connectionManager.getConnections().clear();
    }

    @Test
    public void addConnection() {
        Connection connection = TestObjectBuilder.connection();
        connectionManager.add(connection);

        assertThat(connectionManager.getConnections(), hasItem(connection));
    }

    @Test
    public void poseToLocation() {
        long locationId = new Random().nextLong();
        Connection receiver = connection(locationId);
        connectionManager.add(receiver);

        String text = RandomStringUtils.randomAlphabetic(25);
        connectionManager.poseTo(locationId, "", text);

        assertThat(receiver.getOutputQueue(), hasItem(text));
    }

    @Test
    public void poseToLocationWithCharacterName() {
        long locationId = new Random().nextLong();
        Connection receiver = connection(locationId);
        connectionManager.add(receiver);

        String characterName = RandomStringUtils.randomAlphabetic(16);
        String text = RandomStringUtils.randomAlphabetic(25);
        connectionManager.poseTo(locationId, characterName, text);

        assertThat(receiver.getOutputQueue(), hasItem(characterName + text));
    }

    @Test
    public void poseToAllAtLocation() {
        long locationId = new Random().nextLong();
        for (int i = 0; i < 3; i++) {
            connectionManager.add(connection(locationId));
        }

        String text = RandomStringUtils.randomAlphabetic(25);
        System.out.println(text);
        connectionManager.poseTo(locationId, "", text);

        for (Connection connection : connectionManager.getConnections()) {
            assertThat(connection.getOutputQueue(), hasItem(text));
        }
    }

    @Test
    public void doNotSendToCharactersAtOtherLocations() {
        long locationId = new Random().nextLong();
        Connection receiver = connection(locationId);
        Connection nonReceiver = connection(locationId * 2);
        connectionManager.add(receiver);
        connectionManager.add(nonReceiver);

        String text = RandomStringUtils.randomAlphabetic(25);
        connectionManager.poseTo(locationId, "", text);

        assertThat(receiver.getOutputQueue(), hasItem(text));
        assertThat(nonReceiver.getOutputQueue(), not(hasItem(text)));
    }

    @Test
    public void poseToAllAtLocationExcept() {
        long locationId = new Random().nextLong();
        Connection included = connection(locationId);
        Connection except = connection(locationId);

        connectionManager.add(included);
        connectionManager.add(except);

        String text = RandomStringUtils.randomAlphabetic(25);
        connectionManager.poseTo(locationId, "", text, except);

            assertThat(included.getOutputQueue(), hasItem(text));
        assertThat(except.getOutputQueue(), not(hasItem(text)));
    }

    @Test
    public void poseToAllAtLocationExceptMultiple() {
        long locationId = new Random().nextLong();
        Connection included = connection(locationId);
        Connection except1 = connection(locationId);
        Connection except2 = connection(locationId);

        connectionManager.add(included);
        connectionManager.add(except1);
        connectionManager.add(except2);

        String text = RandomStringUtils.randomAlphabetic(25);
        connectionManager.poseTo(locationId, "", text, except1, except2);

        assertThat(included.getOutputQueue(), hasItem(text));
        assertThat(except1.getOutputQueue(), not(hasItem(text)));
    }

    private Connection connection(long locationId) {
        GameCharacter character = new GameCharacter();
        character.setLocationId(locationId);
        return TestObjectBuilder.connection().withCharacter(character);
    }
}
