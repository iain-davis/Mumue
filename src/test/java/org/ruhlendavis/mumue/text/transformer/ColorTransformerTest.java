package org.ruhlendavis.mumue.text.transformer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ColorTransformerTest {
    private final ColorTransformer transformer = new ColorTransformer();

    @Test
    public void neverReturnNull() {
        assertNotNull(transformer.transform(null));
    }

    @Test
    public void replaceOneColorWithEscapeCodes() {
        transformer.setColorMode(ColorMode.AnsiColor);
        String text = Color.CyanForeground.getGlowCode();

        String result = transformer.transform(text);

        String expected = ColorTransformer.prefix + Color.CyanForeground.getAnsiCode() + ColorTransformer.suffix;
        assertThat(result, equalTo(expected));
    }

    @Test
    public void replaceTwoIdenticalColorsWithEscapeCodes() {
        transformer.setColorMode(ColorMode.AnsiColor);
        String text = Color.CyanForeground.getGlowCode() + Color.CyanForeground.getGlowCode();

        String result = transformer.transform(text);

        String first = ColorTransformer.prefix + Color.CyanForeground.getAnsiCode() + ColorTransformer.suffix;
        String second = ColorTransformer.prefix + Color.CyanForeground.getAnsiCode() + ColorTransformer.suffix;
        String expected = first + second;
        assertThat(result, equalTo(expected));
    }

    @Test
    public void replaceTwoColorsWithEscapeCodes() {
        transformer.setColorMode(ColorMode.AnsiColor);
        String text = Color.CyanForeground.getGlowCode() + Color.DarkCyanForeground.getGlowCode();

        String first = ColorTransformer.prefix + Color.CyanForeground.getAnsiCode() + ColorTransformer.suffix;
        String second = ColorTransformer.prefix + Color.DarkCyanForeground.getAnsiCode() + ColorTransformer.suffix;
        String expected = first + second;

        String result = transformer.transform(text);

        assertThat(result, equalTo(expected));
    }

    @Test
    public void stripColorCodesWithColorNone() {
        transformer.setColorMode(ColorMode.None);

        String text = Color.CyanForeground.getGlowCode();

        String result = transformer.transform(text);

        assertThat(result, equalTo(""));
    }
}
