package org.ruhlendavis.meta.properties;

public class FloatProperty extends Property {
    private Double value = 0.0;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
