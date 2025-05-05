package org.mumue.mumue.text;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TextQueueTest {
    private final TextQueue textQueue = new TextQueue();

    @Test
    void pushAddsTextToQueue() {
        String text = RandomStringUtils.insecure().nextAlphabetic(17);

        textQueue.push(text);

        assertThat(textQueue.peek(), equalTo(text));
    }

    @Test
    void popReturnsText() {
        String text = RandomStringUtils.insecure().nextAlphabetic(17);

        textQueue.push(text);

        assertThat(textQueue.pop(), equalTo(text));
    }

    @Test
    void popRemovesText() {
        String text = RandomStringUtils.insecure().nextAlphabetic(17);

        textQueue.push(text);

        assertThat(textQueue.pop(), equalTo(text));
        assertThat(textQueue.isEmpty(), equalTo(true));
    }

    @Test
    void pushAddsMultipleTextToQueue() {
        String text1 = RandomStringUtils.insecure().nextAlphabetic(17);
        textQueue.push(text1);
        String text2 = RandomStringUtils.insecure().nextAlphabetic(17);
        textQueue.push(text2);

        assertThat(textQueue.size(), equalTo(2));
        assertThat(textQueue.pop(), equalTo(text1));
        assertThat(textQueue.pop(), equalTo(text2));
    }
}
