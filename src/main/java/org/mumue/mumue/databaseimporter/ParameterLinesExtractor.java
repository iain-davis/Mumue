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
        for (int i = SOURCE_FIRST_PARAMETER_INDEX; i <= SOURCE_PARAMETER_COUNT_INDEX + parameterCount; i++) {
            parameters.add(lines.get(i));
        }
        return parameters;
    }
}
