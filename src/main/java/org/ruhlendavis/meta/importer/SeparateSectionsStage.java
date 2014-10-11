package org.ruhlendavis.meta.importer;

import java.util.List;

public class SeparateSectionsStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        List<String> sourceLines = bucket.getSourceLines();
        if (sourceLines.size() == 0 || !"***Foxen5 TinyMUCK DUMP Format***".equals(sourceLines.get(0))) {
            bucket.setFailed(true);
            return;
        }
        bucket.setDatabaseItemCount(Long.parseLong(sourceLines.get(1)));
    }
}
