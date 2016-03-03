package org.mumue.mumue.databaseimporter.testapi;

import java.util.Arrays;
import java.util.List;

public class DatabaseLinesBuilder implements LineBuilder {
    private String format = "***Foxen5 TinyMUCK DUMP Format***";
    private int itemCount;

    @Override
    public List<String> getLines() {
        return Arrays.asList(format, String.valueOf(itemCount));
    }

    public DatabaseLinesBuilder withFormat(String format) {
        this.format = format;
        return this;
    }

    public DatabaseLinesBuilder withDatabaseItemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }
}
