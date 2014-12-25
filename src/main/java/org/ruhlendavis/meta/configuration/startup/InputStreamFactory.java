package org.ruhlendavis.meta.configuration.startup;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class InputStreamFactory {
    private FileFactory fileFactory = new FileFactory();

    public InputStream create(String path) {
        try {
            return FileUtils.openInputStream(FileUtils.getFile(fileFactory.create(path)));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}