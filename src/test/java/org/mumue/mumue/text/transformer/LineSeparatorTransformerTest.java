package org.mumue.mumue.text.transformer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class LineSeparatorTransformerTest {
    private final LineSeparatorTransformer transformer = new LineSeparatorTransformer();

    @Test
    public void transformWithNullReturnsEmptyString() {
        assertThat(transformer.transform(null), equalTo(""));
    }

    @Test
    public void transformWithTextReturnsText() {
        String input = RandomStringUtils.randomAlphanumeric(17);

        assertThat(transformer.transform(input), equalTo(input));
    }

    @Test
    public void transformWithSlashNReturnsTextWithNewline() {
        String before = RandomStringUtils.randomAlphanumeric(14);
        String after = RandomStringUtils.randomAlphanumeric(3);
        String input = before + "\\n" + after;
        String expected = before + "\n" + after;

        assertThat(transformer.transform(input), equalTo(expected));
    }

    @Test
    public void transformWithTwoSlashNReturnsTextWithTwoNewlines() {
        String before = RandomStringUtils.randomAlphanumeric(14);
        String after = RandomStringUtils.randomAlphanumeric(3);
        String input = before + "\\n\\n" + after;
        String expected = before + "\n\n" + after;

        assertThat(transformer.transform(input), equalTo(expected));
    }

    @Test
    public void transformWithSlashRReturnsTextWithCarriageReturn() {
        String before = RandomStringUtils.randomAlphanumeric(14);
        String after = RandomStringUtils.randomAlphanumeric(3);
        String input = before + "\\r" + after;
        String expected = before + "\r" + after;

        assertThat(transformer.transform(input), equalTo(expected));
    }
}
