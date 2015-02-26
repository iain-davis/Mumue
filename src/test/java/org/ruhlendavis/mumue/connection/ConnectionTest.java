package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConnectionTest {
    Connection connection = new Connection();

    @Test
    public void inputQueueNotNull() {
        assertNotNull(connection.getInputQueue());
    }
    @Test
    public void outputQueueNotNull() {
        assertNotNull(connection.getOutputQueue());
    }
    @Test
    public void characterNotNull() {
        assertNotNull(connection.getCharacter());
    }

    @Test
    public void optionMapEmptyByDefault() {
        assertNotNull(connection.getMenuOptionIds());

        assertThat(connection.getMenuOptionIds().size(), equalTo(0));
    }
}
