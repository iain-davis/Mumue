package org.mumue.mumue.databaseimporter;

import java.io.File;

public class ImportConfiguration {
    private String path;
    private File file;

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
