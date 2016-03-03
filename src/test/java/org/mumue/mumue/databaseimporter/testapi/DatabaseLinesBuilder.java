package org.mumue.mumue.databaseimporter.testapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseLinesBuilder implements LineBuilder {
    private final ParameterLinesBuilder parameterLinesBuilder;
    private String format = "***Foxen5 TinyMUCK DUMP Format***";
    private int itemCount;
    private String version = "1";

    public DatabaseLinesBuilder(ParameterLinesBuilder parameterLinesBuilder) {
        this.parameterLinesBuilder = parameterLinesBuilder;
    }

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        lines.add(format);
        lines.add(String.valueOf(itemCount));
        lines.add(version);
        List<String> parameterLines = parameterLinesBuilder.getLines();
        lines.add(String.valueOf(parameterLines.size()));
        lines.addAll(parameterLines);
        return lines;
    }

    public DatabaseLinesBuilder withFormat(String format) {
        this.format = format;
        return this;
    }

    public DatabaseLinesBuilder withDatabaseItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public DatabaseLinesBuilder withFormatVersion(String version) {
        this.version = version;
        return this;
    }
}
