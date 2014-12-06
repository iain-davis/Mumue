package org.ruhlendavis.meta.configuration.file;

import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.Test;

public class FileFactoryTest {
    @Test
    public void createFileReturnsFile() throws URISyntaxException {
        FileFactory fileFactory = new FileFactory();
        String name = "configuration.properties";
        String path = Resources.getResource("org/ruhlendavis/meta/configuration/file/" + name).toURI().getPath();
        File file = fileFactory.createFile(path);
        assertEquals(name, file.getName());
    }
}
