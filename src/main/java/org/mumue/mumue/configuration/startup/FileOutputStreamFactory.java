package org.mumue.mumue.configuration.startup;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

class FileOutputStreamFactory {
    public OutputStream create(File file) {
        try {
            return FileUtils.openOutputStream(FileUtils.getFile(file));
        } catch (IOException exception) {
            throw new RuntimeException("Exception while creating file output stream for path '" + file.getPath() + "'", exception);
        }
    }
}
