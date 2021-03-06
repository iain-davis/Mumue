package org.mumue.mumue.importer;

import java.util.ArrayList;
import java.util.List;

import org.mumue.mumue.importer.stages.ContentsChainStage;
import org.mumue.mumue.importer.stages.GenerateRawComponentsStage;
import org.mumue.mumue.importer.stages.LinkSourceChainStage;
import org.mumue.mumue.importer.stages.LoadLinesStage;
import org.mumue.mumue.importer.stages.PlayerGenerationStage;
import org.mumue.mumue.importer.stages.ProcessComponentLinesStage;
import org.mumue.mumue.importer.stages.SeparateSectionsStage;

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
        stages.add(new PlayerGenerationStage());
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
