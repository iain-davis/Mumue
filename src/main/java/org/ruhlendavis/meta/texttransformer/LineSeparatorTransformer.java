package org.ruhlendavis.meta.texttransformer;

public class LineSeparatorTransformer implements TextTransformer {
    @Override
    public String transform(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r");
    }
}
