package org.mumue.mumue.databaseimporter;

import java.util.List;

class DatabaseImporter {
    private final ComponentCountExtractor componentCountExtractor;
    private final LineLoader lineLoader;
    private final ParameterLinesExtractor parameterLinesExtractor;

    DatabaseImporter(ComponentCountExtractor componentCountExtractor, LineLoader lineLoader, ParameterLinesExtractor parameterLinesExtractor) {
        this.componentCountExtractor = componentCountExtractor;
        this.lineLoader = lineLoader;
        this.parameterLinesExtractor = parameterLinesExtractor;
    }

    public void importUsing(ImportConfiguration importConfiguration) {
        List<String> sourceLines = lineLoader.loadFrom(importConfiguration.getFile());
        long componentCount = componentCountExtractor.extract(sourceLines);
        List<String> parameterLines = parameterLinesExtractor.extract(sourceLines);
    }
}
