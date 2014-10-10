package org.ruhlendavis.meta.importer;

public class Importer {
    public void run(String file) {
        ImportBucket bucket = new ImportBucket();
        bucket.setFile(file);
        new GenerateRawComponentsStage().run(bucket);
        new ProcessLinesStage().run(bucket);
    }
}
