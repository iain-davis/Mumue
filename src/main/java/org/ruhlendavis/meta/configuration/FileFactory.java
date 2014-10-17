package org.ruhlendavis.meta.configuration;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class FileFactory {
    public File createFile(String path) {
        return FileUtils.getFile(path);
    }
}
