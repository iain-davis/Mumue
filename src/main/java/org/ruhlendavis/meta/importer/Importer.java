package org.ruhlendavis.meta.importer;

import java.util.ArrayList;
import java.util.List;

public class Importer {
    private List<ImporterStage> stages = new ArrayList<>();

    public Importer() {
        stages.add(new LoadLinesStage());
    }

    public void run(String file) {
        ImportBucket bucket = new ImportBucket();
        bucket.setFile(file);
        for (ImporterStage stage : stages) {
            stage.run(bucket);
        }
    }

    protected void setStages(List<ImporterStage> stages) {
        this.stages = stages;
    }
}
