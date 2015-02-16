package org.ruhlendavis.meta.texttransformer;

import java.util.ArrayList;
import java.util.List;

public class TransformerEngine {
    private List<TextTransformer> transformers = new ArrayList<>();

    public String transform(String input) {
        if (input == null) {
            return "";
        }

        for (TextTransformer transformer : transformers) {
            input = transformer.transform(input);
        }
        return input;
    }

    public void add(TextTransformer transformer) {
        transformers.add(transformer);
    }
}
