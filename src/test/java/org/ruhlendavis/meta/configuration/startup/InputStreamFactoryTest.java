package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InputStreamFactoryTest {
    private final InputStreamFactory factory = new InputStreamFactory();
    private final OutputStreamFactory outputStreamFactory = new OutputStreamFactory();

    @Test
    public void createInputStreamCreatesStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        OutputStream output = outputStreamFactory.create(path);
        InputStream input = factory.create(path);
        assertNotNull(input);
        IOUtils.closeQuietly(input);
        IOUtils.closeQuietly(output);
        FileUtils.deleteQuietly(FileUtils.getFile(path));
    }
}
