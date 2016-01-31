package org.mumue.mumue.databaseimporter;

import java.util.List;

class DatabaseImporter {
    private static final int FILE_FORMAT_LINE_INDEX = 0;
    private static final int FILE_FORMAT_VERSION_LINE_INDEX = 2;
    private final ComponentCountExtractor componentCountExtractor;
    private final LineLoader lineLoader;
    private final ParameterLinesExtractor parameterLinesExtractor;

    DatabaseImporter(ComponentCountExtractor componentCountExtractor, LineLoader lineLoader, ParameterLinesExtractor parameterLinesExtractor) {
        this.componentCountExtractor = componentCountExtractor;
        this.lineLoader = lineLoader;
        this.parameterLinesExtractor = parameterLinesExtractor;
    }

    public ImportResults importUsing(ImportConfiguration importConfiguration) {
        ImportResults importResults = new ImportResults();
        List<String> sourceLines = lineLoader.loadFrom(importConfiguration.getFile());

        if (isValidFormat(sourceLines)) {
            importResults.setComponentCount(componentCountExtractor.extract(sourceLines));
            List<String> parameterLines = parameterLinesExtractor.extract(sourceLines);

            importResults.setParameterCount(parameterLines.size());
        }
        return importResults;
    }

    private boolean isValidFormat(List<String> sourceLines) {
        return !sourceLines.isEmpty() && isValidFormat(sourceLines.get(FILE_FORMAT_LINE_INDEX)) && isValidVersion(sourceLines.get(FILE_FORMAT_VERSION_LINE_INDEX));
    }

    private boolean isValidVersion(String line) {
        return "1".equals(line);
    }

    private boolean isValidFormat(String line) {
        return "***Foxen5 TinyMUCK DUMP Format***".equals(line);
    }
}
