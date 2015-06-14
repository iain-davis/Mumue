package org.mumue.mumue.configuration.startup;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;

import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.acceptance.TestConstants;

public class FileFactoryTest {
    @Test
    public void createFileReturnsFile() throws URISyntaxException {
        FileFactory fileFactory = new FileFactory();
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        File file = fileFactory.create(path);

        Assert.assertEquals(ConfigurationDefaults.CONFIGURATION_PATH, file.getName());
    }
}
