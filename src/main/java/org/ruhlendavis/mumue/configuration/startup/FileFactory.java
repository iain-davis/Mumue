package org.ruhlendavis.mumue.configuration.startup;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FileFactory {
    public File create(String path) {
        return FileUtils.getFile(path);
    }
}