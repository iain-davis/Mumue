package org.mumue.mumue.databaseimporter;

import java.util.List;
import jakarta.inject.Inject;

class DatabaseImporter {
    private static final int FILE_FORMAT_LINE_INDEX = 0;
    private static final int FILE_FORMAT_VERSION_LINE_INDEX = 2;
    private final LineLoader lineLoader;
    private final ParametersImporter parametersImporter;
    private final UniverseImporter universeImporter;
    private final ComponentsImporter componentsImporter;

    @Inject
    DatabaseImporter(ComponentsImporter componentsImporter, LineLoader lineLoader, ParametersImporter parametersImporter, UniverseImporter universeImporter) {
        this.componentsImporter = componentsImporter;
        this.lineLoader = lineLoader;
        this.parametersImporter = parametersImporter;
        this.universeImporter = universeImporter;
    }

    public ImportResults importUsing(ImportConfiguration importConfiguration) {
        return importFrom(lineLoader.loadFrom(importConfiguration.getFile()));
    }

    private ImportResults importFrom(List<String> sourceLines) {
        ImportResults importResults = new ImportResults();
        if (isValidFormat(sourceLines)) {
            importResults.setParameters(parametersImporter.importFrom(sourceLines));
            importResults.setUniverse(universeImporter.importFrom(importResults.getParameters()));
            List<String> lines = getComponentLines(sourceLines, importResults.getParameters().size());
            importResults.setComponents(componentsImporter.importFrom(lines, importResults.getUniverse()));
        }
        return importResults;
    }

    private List<String> getComponentLines(List<String> sourceLines, int parametersSize) {
        int startOfComponentsIndex = ParametersImporter.SOURCE_FIRST_PARAMETER_INDEX + parametersSize;
        int endOfComponentsIndex = sourceLines.indexOf("***END OF DUMP***");
        return sourceLines.subList(startOfComponentsIndex, endOfComponentsIndex);
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
