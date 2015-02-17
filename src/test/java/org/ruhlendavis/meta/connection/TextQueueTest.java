package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class TextQueueTest {
    private final TextQueue textQueue = new TextQueue();

    @Test
    public void pushAddsTextToQueue() {
        String text = RandomStringUtils.randomAlphabetic(17);

        textQueue.push(text);

        assertTrue(textQueue.hasAny());
        assertThat(textQueue.peek(), equalTo(text));
    }

    @Test
    public void popReturnsText() {
        String text = RandomStringUtils.randomAlphabetic(17);

        textQueue.push(text);

        assertThat(textQueue.pop(), equalTo(text));
    }

    @Test
    public void popRemovesText() {
        String text = RandomStringUtils.randomAlphabetic(17);

        textQueue.push(text);

        assertThat(textQueue.pop(), equalTo(text));
        assertTrue(textQueue.isEmpty());
    }

    @Test
    public void pushAddsMultipleTextToQueue() {
        String text1 = RandomStringUtils.randomAlphabetic(17);
        textQueue.push(text1);
        String text2 = RandomStringUtils.randomAlphabetic(17);
        textQueue.push(text2);

        assertThat(textQueue.size(), equalTo(2));
        assertThat(textQueue.pop(), equalTo(text1));
        assertThat(textQueue.pop(), equalTo(text2));
    }
}
