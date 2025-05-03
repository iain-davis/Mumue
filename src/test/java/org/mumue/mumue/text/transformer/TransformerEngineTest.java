package org.mumue.mumue.text.transformer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class TransformerEngineTest {
    private final TransformerEngine engine = new TransformerEngine();

    @Test
    public void returnEmptyStringWithNull() {
        assertThat(engine.transform(null), equalTo(""));
    }

    @Test
    public void returnStringWithoutTransformers() {
        String input = RandomStringUtils.insecure().nextAlphabetic(17);
        assertThat(engine.transform(input), equalTo(input));
    }

    @Test
    public void shouldTransformUsingProvidedTransformers() {
        String input = RandomStringUtils.randomAlphanumeric(16);
        final String expected = RandomStringUtils.randomAlphanumeric(17);
        TextTransformer transformer = input1 -> expected;

        engine.add(transformer);

        assertThat(engine.transform(input), equalTo(expected));
    }
}
