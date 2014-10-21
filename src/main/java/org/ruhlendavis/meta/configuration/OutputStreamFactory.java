package org.ruhlendavis.meta.configuration;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;

public class OutputStreamFactory {
    private FileFactory fileFactory = new FileFactory();
    public OutputStream createOutputStream(String path) {
        try {
            return FileUtils.openOutputStream(FileUtils.getFile(fileFactory.createFile(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
