package org.ruhlendavis.mumue.componentsold.properties;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class ReferenceProperty extends Property {
    Long value = GlobalConstants.REFERENCE_UNKNOWN;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public ReferenceProperty withValue(Long value) {
        this.value = value;
        return this;
    }
}
