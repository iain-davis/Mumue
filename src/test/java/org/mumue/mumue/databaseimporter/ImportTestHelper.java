package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.databaseimporter.testapi.DatabaseItemLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.ParameterLinesBuilder;

import java.util.ArrayList;
import java.util.List;

class ImportTestHelper {
    private static final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    public static List<String> generateLines(long randomParameters, String muckName, long startingLocation, int components) {
        List<String> parameterLines = new ParameterLinesBuilder().withMuckName(muckName).withPlayerStart(startingLocation).withAdditionalRandomParameters(randomParameters).getLines();
        List<String> lines = new ArrayList<>(parameterLines);
        for (int i = 0; i < components; i++) {
            long id = i;
            if (i > 1) id = id * 2;
            lines.addAll(databaseItemLinesBuilder.withId(id).getLines());
        }
        lines.add("***END OF DUMP***");
        return lines;
    }
}
