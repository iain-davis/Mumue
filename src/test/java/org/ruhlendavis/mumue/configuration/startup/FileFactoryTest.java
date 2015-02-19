package org.ruhlendavis.mumue.configuration.startup;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.TestConstants;
import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

public class FileFactoryTest {
    @Test
    public void createFileReturnsFile() throws URISyntaxException {
        FileFactory fileFactory = new FileFactory();
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        File file = fileFactory.create(path);
        assertEquals(ConfigurationDefaults.CONFIGURATION_PATH, file.getName());
    }
}
