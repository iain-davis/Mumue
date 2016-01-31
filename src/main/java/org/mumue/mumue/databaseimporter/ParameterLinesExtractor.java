package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;

class ParameterLinesExtractor {
    private static final int SOURCE_PARAMETER_COUNT_INDEX = 3;
    private static final int SOURCE_FIRST_PARAMETER_INDEX = 4;

    public List<String> extract(List<String> lines) {
        List<String> parameters = new ArrayList<>();
        if (lines.isEmpty()) {
            return parameters;
        }
        int parameterCount = Integer.parseInt(lines.get(SOURCE_PARAMETER_COUNT_INDEX));
        int toIndex = SOURCE_PARAMETER_COUNT_INDEX + parameterCount + 1;
        return lines.subList(SOURCE_FIRST_PARAMETER_INDEX, toIndex);
    }
}
