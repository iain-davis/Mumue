package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class InputInterpreterTest {
    Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
    Collection<String> outputQueue = new ConcurrentLinkedQueue<>();

    private final InputInterpreter inputInterpreter = new InputInterpreter(inputQueue, outputQueue);

    @Test
    public void copyLineToOutput() {
        String line = RandomStringUtils.randomAlphabetic(17);
        inputQueue.add(line);

        inputInterpreter.prepare();
        inputInterpreter.execute();
        inputInterpreter.cleanup();

        assertThat(outputQueue, contains(line));
    }

    @Test
    public void removeLineFromInput() {
        String line = RandomStringUtils.randomAlphabetic(17);
        inputQueue.add(line);

        inputInterpreter.execute();

        assertThat(inputQueue, not(contains(line)));
    }

    @Test
    public void copyOnlyTheFirst() {
        String line1 = RandomStringUtils.randomAlphabetic(17);
        String line2 = RandomStringUtils.randomAlphabetic(13);
        inputQueue.add(line1);
        inputQueue.add(line2);

        inputInterpreter.execute();

        assertThat(outputQueue, contains(line1));
        assertThat(inputQueue, not(contains(line1)));
        assertThat(inputQueue, contains(line2));
        assertThat(outputQueue, not(contains(line2)));
    }

    @Test
    public void doNotHaveDifficultyWithEmptyInput() {
        inputQueue.clear();

        inputInterpreter.execute();

        assertTrue(outputQueue.isEmpty());
    }
}
