package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.databaseimporter.testapi.DatabaseItemLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.ParameterLinesBuilder;

import java.util.ArrayList;
import java.util.List;

class ImportTestHelper {
    private static final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    public static List<String> generateLines(long randomParameters, String muckName, long startingLocation, int components) {
        List<String> lines = new ArrayList<>();
        lines.addAll(new ParameterLinesBuilder().withMuckName(muckName).withPlayerStart(startingLocation).withAdditionalRandomParameters(randomParameters).getLines());
        for (int i = 0; i < components; i++) {
            lines.addAll(databaseItemLinesBuilder.withRandomId().build());
        }
        lines.add("***END OF DUMP***");
        return lines;
    }
}
