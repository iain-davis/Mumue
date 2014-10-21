package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertNotNull;

import java.io.OutputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class OutputStreamFactoryTest {
    @Mock private FileFactory fileFactory = new FileFactory();
    @InjectMocks private OutputStreamFactory factory = new OutputStreamFactory();

    @Test
    public void createOutputStreamCreatesStream() {
        String path = RandomStringUtils.randomAlphabetic(13);
        OutputStream output = factory.createOutputStream(path);
        assertNotNull(output);
    }
}
