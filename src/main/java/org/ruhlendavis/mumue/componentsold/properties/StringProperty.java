package org.ruhlendavis.mumue.componentsold.properties;

public class StringProperty extends Property {
    private String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringProperty withValue(String value) {
        this.value = value;
        return this;
    }
}
