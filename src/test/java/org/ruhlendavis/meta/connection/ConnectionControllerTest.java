package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ConnectionControllerTest {
    TextQueue inputQueue = new TextQueue();
    TextQueue outputQueue = new TextQueue();

    private final ConnectionController connectionController = new ConnectionController(inputQueue, outputQueue);

    @Test
    public void copyLineToOutput() {
        String line = RandomStringUtils.randomAlphabetic(17);
        inputQueue.push(line);

        connectionController.prepare();
        connectionController.execute();
        connectionController.cleanup();

        assertThat(outputQueue, contains(line));
    }

    @Test
    public void removeLineFromInput() {
        String line = RandomStringUtils.randomAlphabetic(17);
        inputQueue.push(line);

        connectionController.execute();

        assertThat(inputQueue, not(contains(line)));
    }

    @Test
    public void copyOnlyTheFirst() {
        String line1 = RandomStringUtils.randomAlphabetic(17);
        String line2 = RandomStringUtils.randomAlphabetic(13);
        inputQueue.push(line1);
        inputQueue.push(line2);

        connectionController.execute();

        assertThat(outputQueue, contains(line1));
        assertThat(inputQueue, not(contains(line1)));
        assertThat(inputQueue, contains(line2));
        assertThat(outputQueue, not(contains(line2)));
    }

    @Test
    public void doNotHaveDifficultyWithEmptyInput() {
        connectionController.execute();

        assertTrue(outputQueue.isEmpty());
    }
}
