package org.mumue.mumue.databaseimporter;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

class ParametersExtractor {
    private static final int SOURCE_PARAMETER_COUNT_INDEX = 3;
    private static final int SOURCE_FIRST_PARAMETER_INDEX = 4;

    public Properties extract(List<String> lines) {
        if (lines.isEmpty()) {
            return new Properties();
        }
        int parameterCount = Integer.parseInt(lines.get(SOURCE_PARAMETER_COUNT_INDEX));
        int toIndex = SOURCE_PARAMETER_COUNT_INDEX + parameterCount + 1;

        String parameterLines = lines.subList(SOURCE_FIRST_PARAMETER_INDEX, toIndex).stream().collect(Collectors.joining("\r\n"));
        return readProperties(parameterLines);
    }

    private Properties readProperties(String parameterLines) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(parameterLines));
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
