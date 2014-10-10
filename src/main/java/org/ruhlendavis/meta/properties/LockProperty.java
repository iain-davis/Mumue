package org.ruhlendavis.meta.properties;

public class LockProperty extends Property {
    private String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LockProperty withValue(String value) {
        this.value = value;
        return this;
    }
}
