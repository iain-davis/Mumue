package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.Test;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class FileFactoryTest {
    @Test
    public void createFileReturnsFile() throws URISyntaxException {
        FileFactory fileFactory = new FileFactory();
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/startup/" + ConfigurationDefaults.CONFIGURATION_PATH).toURI().getPath();
        File file = fileFactory.createFile(path);
        assertEquals(ConfigurationDefaults.CONFIGURATION_PATH, file.getName());
    }
}
