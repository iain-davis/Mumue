package org.ruhlendavis.meta;

import org.junit.Test;
import org.ruhlendavis.meta.importer.Importer;
import org.ruhlendavis.meta.listener.Listener;

public class SystemTest {

    @Test
    public void tryIt() {
        Importer importer = new Importer();
        importer.run("C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\university.db");

        Listener listener = new Listener().withPort(9999).withBucket(importer.getBucket());
        new Thread(listener).start();
        while(true);
    }
}
