package org.ruhlendavis.meta.properties;

public class IntegerProperty extends Property {
    private Long value = 0L;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
