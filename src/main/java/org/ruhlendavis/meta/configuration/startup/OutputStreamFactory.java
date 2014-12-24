package org.ruhlendavis.meta.configuration.startup;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public class OutputStreamFactory {
    private FileFactory fileFactory = new FileFactory();
    public OutputStream create(String path) {
        try {
            return FileUtils.openOutputStream(FileUtils.getFile(fileFactory.create(path)));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
