package org.mumue.mumue.databaseimporter;

import java.util.List;

class ComponentCountExtractor {
    private static final int SOURCE_ITEM_COUNT_INDEX = 1;
    public int extract(List<String> lines) {
        if (lines.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(lines.get(SOURCE_ITEM_COUNT_INDEX));
    }
}
