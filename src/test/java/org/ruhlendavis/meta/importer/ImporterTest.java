package org.ruhlendavis.meta.importer;

import org.junit.Test;

public class ImporterTest {
    private Importer importer = new Importer();

    @Test
    public void f() {
        importer.run("C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\glow.db");
    }
}
