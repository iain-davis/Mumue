package org.ruhlendavis.meta.importer;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class LoadLinesStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        try {
            bucket.setSourceLines(FileUtils.readLines(FileUtils.getFile(bucket.getFile())));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
