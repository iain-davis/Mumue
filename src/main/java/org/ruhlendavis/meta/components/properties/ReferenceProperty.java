package org.ruhlendavis.meta.components.properties;

import org.ruhlendavis.meta.GlobalConstants;

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
