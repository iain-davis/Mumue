package org.ruhlendavis.meta.properties;

public class StringProperty extends Property {
    private String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}