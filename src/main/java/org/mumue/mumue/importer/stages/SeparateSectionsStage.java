package org.mumue.mumue.importer.stages;

import java.util.ArrayList;
import java.util.List;

import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.ImporterStage;

public class SeparateSectionsStage extends ImporterStage {
    private static final int SOURCE_VERSION_INDEX = 0;
    private static final int SOURCE_ITEM_COUNT_INDEX = 1;
    private static final int SOURCE_FORMAT_INDEX = 2;
    private static final int SOURCE_PARAMETER_COUNT_INDEX = 3;
    private static final int SOURCE_FIRST_PARAMETER_INDEX = 4;
    private static final int ITEM_LINES_BEFORE_PROPERTIES = 11;

    /*package*/static final int SPACE_CODA_LINES = 3;
    /*package*/static final int ARTIFACT_CODA_LINES = 4;
    /*package*/static final int CHARACTER_CODA_LINES = 4;
    /*package*/static final int LINK_CODA_LINES = 2;
    /*package*/static final int PROGRAM_CODA_LINES = 1;

    private static final int SPACE_TYPE_VALUE = 0;
    private static final int ARTIFACT_TYPE_VALUE = 1;
    private static final int LINK_TYPE_VALUE = 2;
    private static final int CHARACTER_TYPE_VALUE = 3;
    private static final int PROGRAM_TYPE_VALUE = 4;
    private static final int GARBAGE_TYPE_VALUE = 6;

    @Override
    public void run(ImportBucket bucket) {
        List<String> sourceLines = bucket.getSourceLines();
        if (sourceLines.size() == 0 || !isKnownVersion(sourceLines) || !isValidFormat(sourceLines)) {
            bucket.setFailed(true);
            return;
        }
        bucket.setDatabaseItemCount(Long.parseLong(sourceLines.get(SOURCE_ITEM_COUNT_INDEX)));
        bucket.setParameterCount(Integer.parseInt(sourceLines.get(SOURCE_PARAMETER_COUNT_INDEX)));

        int referenceIndex = SOURCE_FIRST_PARAMETER_INDEX + bucket.getParameterCount();
        for (int i = SOURCE_FIRST_PARAMETER_INDEX; i < referenceIndex; i++) {
            bucket.getParameterLines().add(sourceLines.get(i));
        }
        while(!"***END OF DUMP***".equals(sourceLines.get(referenceIndex))) {
            Long reference = parseReference(sourceLines.get(referenceIndex));
            List<String> item = new ArrayList<>();
            addLines(sourceLines, referenceIndex, item, ITEM_LINES_BEFORE_PROPERTIES);
            int currentIndex = referenceIndex + ITEM_LINES_BEFORE_PROPERTIES;
            String currentLine = sourceLines.get(currentIndex);
            while (!currentLine.equals("*End*")) {
                item.add(currentLine);
                currentIndex++;
                currentLine = sourceLines.get(currentIndex);
            }
            item.add(currentLine);
            long type = determineType(item.get(ITEM_FLAGS_INDEX));
            currentIndex++;
            if (type == SPACE_TYPE_VALUE) {
                addLines(sourceLines, currentIndex, item, SPACE_CODA_LINES);
                currentIndex = currentIndex + SPACE_CODA_LINES;
            } else if (type == ARTIFACT_TYPE_VALUE) {
                addLines(sourceLines, currentIndex, item, ARTIFACT_CODA_LINES);
                currentIndex = currentIndex + ARTIFACT_CODA_LINES;
            } else if (type == CHARACTER_TYPE_VALUE) {
                addLines(sourceLines, currentIndex, item, CHARACTER_CODA_LINES);
                currentIndex = currentIndex + CHARACTER_CODA_LINES;
            } else if (type == LINK_TYPE_VALUE) {
                int linkDestinations = Integer.parseInt(sourceLines.get(currentIndex));
                addLines(sourceLines, currentIndex, item, LINK_CODA_LINES + linkDestinations);
                currentIndex = currentIndex + LINK_CODA_LINES + linkDestinations;
            } else if (type == PROGRAM_TYPE_VALUE) {
                addLines(sourceLines, currentIndex, item, PROGRAM_CODA_LINES);
                currentIndex = currentIndex + PROGRAM_CODA_LINES;
            } else if (type == GARBAGE_TYPE_VALUE) {
                referenceIndex = currentIndex;
                continue;
            }
            referenceIndex = currentIndex;
            bucket.getComponentLines().put(reference, item);
        }
        bucket.getSourceLines().clear();
    }

    private void addLines(List<String> sourceLines, int startIndex, List<String> item, int count) {
        for (int i = 0; i < count; i++) {
            item.add(sourceLines.get(startIndex + i));
        }
    }

    private boolean isValidFormat(List<String> sourceLines) {
        return "1".equals(sourceLines.get(SOURCE_FORMAT_INDEX));
    }

    private boolean isKnownVersion(List<String> sourceLines) {
        return "***Foxen5 TinyMUCK DUMP Format***".equals(sourceLines.get(SOURCE_VERSION_INDEX));
    }
}
