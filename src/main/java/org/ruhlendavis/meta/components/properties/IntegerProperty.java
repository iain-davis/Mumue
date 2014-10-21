package org.ruhlendavis.meta.components.properties;

public class IntegerProperty extends Property {
    private Long value = 0L;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public IntegerProperty withValue(long value) {
        this.value = value;
        return this;
    }
}
