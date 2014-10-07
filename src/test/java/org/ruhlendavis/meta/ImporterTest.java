package org.ruhlendavis.meta;

import org.junit.Test;

public class ImporterTest {
    private Importer importer = new Importer();
    private String inputFile = "C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\glow.db";

    @Test
    public void f() {
        importer.importFromGlow(inputFile);
    }
}
