package org.ruhlendavis.mumue.configuration.startup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

class FileInputStreamFactory {
    public InputStream create(File file) {
        try {
            return FileUtils.openInputStream(FileUtils.getFile(file));
        } catch (IOException exception) {
            throw new RuntimeException("Exception while creating file input stream for path '" + file.getPath() + "'", exception);
        }
    }
}
