package org.mumue.mumue.databaseimporter;

import java.util.List;

class DatabaseImporter {
    private final LineLoader lineLoader;
    private final ParameterLinesExtractor parameterLinesExtractor;

    DatabaseImporter(LineLoader lineLoader, ParameterLinesExtractor parameterLinesExtractor) {
        this.lineLoader = lineLoader;
        this.parameterLinesExtractor = parameterLinesExtractor;
    }

    public void importUsing(ImportConfiguration importConfiguration) {
        List<String> sourceLines = lineLoader.loadFrom(importConfiguration.getFile());
        List<String> parameterLines = parameterLinesExtractor.extract(sourceLines);
    }
}
