package org.ruhlendavis.meta.configuration.file;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.h2.store.fs.FileUtils;
import org.junit.Test;

public class OutputStreamFactoryTest {
    private OutputStreamFactory factory = new OutputStreamFactory();

    @Test
    public void createOutputStreamCreatesStream() throws IOException {
        String path = RandomStringUtils.randomAlphabetic(13);
        OutputStream output = factory.createOutputStream(path);
        assertNotNull(output);
        output.close();
        FileUtils.delete(path);
    }
}
