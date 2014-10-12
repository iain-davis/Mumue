package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.importer.ImportBucket;

public abstract class ImporterStage {
    protected static final int ITEM_FLAGS_INDEX = 5;

    public abstract void run(ImportBucket bucket);

    protected Long parseReference(String reference) {
        if (StringUtils.isBlank(reference)) {
            return GlobalConstants.REFERENCE_UNKNOWN;
        }
        return Long.parseLong(reference.replace("#", ""));
    }

    protected long determineType(String line) {
        return Long.parseLong(line.split(" ")[0]) & 0x7;
    }
}
