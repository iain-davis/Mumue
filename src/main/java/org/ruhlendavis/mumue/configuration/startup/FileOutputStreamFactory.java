package org.ruhlendavis.mumue.configuration.startup;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

class FileOutputStreamFactory {
    private FileFactory fileFactory = new FileFactory();
    public OutputStream create(String path) {
        try {
            return FileUtils.openOutputStream(FileUtils.getFile(fileFactory.create(path)));
        } catch (IOException exception) {
            throw new RuntimeException("Exception while creating file output stream for path '" + path + "'", exception);
        }
    }
}
