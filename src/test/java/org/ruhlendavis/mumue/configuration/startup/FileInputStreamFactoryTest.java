package org.ruhlendavis.mumue.configuration.startup;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileInputStreamFactoryTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final FileInputStreamFactory factory = new FileInputStreamFactory();
    private final FileOutputStreamFactory fileOutputStreamFactory = new FileOutputStreamFactory();

    @Test
    public void createInputStreamCreatesStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        OutputStream output = fileOutputStreamFactory.create(path);
        InputStream input = factory.create(path);
        assertNotNull(input);
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
        FileUtils.deleteQuietly(FileUtils.getFile(path));
    }

    @Test
    public void createInputStreamHandlesException() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception while creating file input stream");
        factory.create("*");
    }
}
