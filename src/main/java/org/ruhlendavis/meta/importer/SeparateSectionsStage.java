package org.ruhlendavis.meta.importer;

import java.util.List;

public class SeparateSectionsStage extends ImporterStage {
    public static final int VERSION_INDEX = 0;
    public static final int ITEM_COUNT_INDEX = 1;
    public static final int FORMAT_INDEX = 2;
    public static final int PARAMETER_COUNT_INDEX = 3;

    @Override
    public void run(ImportBucket bucket) {
        List<String> sourceLines = bucket.getSourceLines();
        if (sourceLines.size() == 0 || !isKnownVersion(sourceLines) || !isValidFormat(sourceLines)) {
            bucket.setFailed(true);
            return;
        }
        bucket.setDatabaseItemCount(Long.parseLong(sourceLines.get(ITEM_COUNT_INDEX)));
        bucket.setParameterCount(Long.parseLong(sourceLines.get(PARAMETER_COUNT_INDEX)));
    }

    private boolean isValidFormat(List<String> sourceLines) {
        return "1".equals(sourceLines.get(FORMAT_INDEX));
    }

    private boolean isKnownVersion(List<String> sourceLines) {
        return "***Foxen5 TinyMUCK DUMP Format***".equals(sourceLines.get(VERSION_INDEX));
    }
}
