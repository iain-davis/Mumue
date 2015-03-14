package org.ruhlendavis.mumue.configuration.startup;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileOutputStreamFactoryTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private FileFactory fileFactory = new FileFactory();
    private FileOutputStreamFactory factory = new FileOutputStreamFactory();

    @Test
    public void createOutputStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        File file = fileFactory.create(path);
        OutputStream output = factory.create(file);
        assertNotNull(output);
        IOUtils.closeQuietly(output);
        FileUtils.deleteQuietly(FileUtils.getFile(path));
    }

    @Test
    public void handleIOException() {
        File file = fileFactory.create("*");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception while creating file output stream for path '*'");

        factory.create(file);
    }
}
