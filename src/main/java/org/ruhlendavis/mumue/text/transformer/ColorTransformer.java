package org.ruhlendavis.mumue.text.transformer;

public class ColorTransformer implements TextTransformer {
    static final String prefix = "\u001B[";
    static final String suffix = "m";

    private ColorMode colorMode = ColorMode.None;

    @Override
    public String transform(String input) {
        if (input == null) {
            return "";
        }

        if (colorMode == ColorMode.None) {
            return removeColorCodes(input);
        }

        for (Color color : Color.values()) {
            input = input.replace(color.getGlowCode(), prefix + color.getAnsiCode() + suffix);
        }

        return input;
    }

    private String removeColorCodes(String input) {
        return input.replaceAll("\\^.*\\^", "");
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
    }
}
