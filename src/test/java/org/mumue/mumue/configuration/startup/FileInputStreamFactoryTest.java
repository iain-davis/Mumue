package org.mumue.mumue.configuration.startup;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileInputStreamFactoryTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private final FileFactory fileFactory = new FileFactory();
    private final FileInputStreamFactory factory = new FileInputStreamFactory();
    private final FileOutputStreamFactory fileOutputStreamFactory = new FileOutputStreamFactory();

    @Test
    public void createStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        File file = fileFactory.create(path);
        OutputStream output = fileOutputStreamFactory.create(file);
        InputStream input = factory.create(file);
        assertNotNull(input);
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
        FileUtils.deleteQuietly(FileUtils.getFile(path));
    }

    @Test
    public void handleIOException() {
        File file = fileFactory.create("*");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception while creating file input stream for path '*'");

        factory.create(file);
    }
}
