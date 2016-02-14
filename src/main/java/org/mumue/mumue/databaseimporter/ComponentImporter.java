package org.mumue.mumue.databaseimporter;

import java.util.List;

class ComponentImporter {
    protected static final int REFERENCE_ID_INDEX = 0;
    protected static final int NAME_INDEX = 1;
    protected static final int LOCATION_INDEX = 2;

    protected long getId(List<String> lines) {
        return Long.parseLong(lines.get(REFERENCE_ID_INDEX).substring(1));
    }

    protected String getName(List<String> lines) {
        return lines.get(NAME_INDEX);
    }

    protected long getLocationId(List<String> lines) {
        return Long.parseLong(lines.get(LOCATION_INDEX));
    }
}
