package org.ruhlendavis.meta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetaMainTest {
    @Test
    public void convertArgumentRequiresAdditionalArguments() {
        String[] arguments = {"--convert", "glow", "glow.db"};
        MetaMain metaMain = new MetaMain(arguments);
    }
}
