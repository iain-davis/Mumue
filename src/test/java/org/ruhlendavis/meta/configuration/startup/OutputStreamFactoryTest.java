package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertNotNull;

import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class OutputStreamFactoryTest {
    private OutputStreamFactory factory = new OutputStreamFactory();

    @Test
    public void createOutputStreamCreatesStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        OutputStream output = factory.create(path);
        assertNotNull(output);
        IOUtils.closeQuietly(output);
        FileUtils.deleteQuietly(FileUtils.getFile(path));
    }
}
