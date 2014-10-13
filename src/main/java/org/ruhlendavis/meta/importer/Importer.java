package org.ruhlendavis.meta.importer;

import org.ruhlendavis.meta.importer.stages.*;

import java.util.ArrayList;
import java.util.List;

public class Importer {
    private List<ImporterStage> stages = new ArrayList<>();
    private ImportBucket bucket = new ImportBucket();

    public Importer() {
        stages.add(new LoadLinesStage());
        stages.add(new SeparateSectionsStage());
        stages.add(new GenerateRawComponentsStage());
        stages.add(new ProcessComponentLinesStage());
        stages.add(new ContentsChainStage());
        stages.add(new LinkSourceChainStage());
    }

    public void run(String file) {
        bucket.setFile(file);
        for (ImporterStage stage : stages) {
            if (bucket.isFailed()) {
                break;
            }
            stage.run(bucket);
        }
    }

    List<ImporterStage> getStages() {
        return stages;
    }

    void setStages(List<ImporterStage> stages) {
        this.stages = stages;
    }
}
