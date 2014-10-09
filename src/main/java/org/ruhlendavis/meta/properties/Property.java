package org.ruhlendavis.meta.properties;

import java.util.HashSet;
import java.util.Set;

public class Property {
    private Property parent;
    private Set<Property> childProperties = new HashSet<>();

    private String name = "";
    private PropertyType type;

    private Long databaseReference = 0L;
    private Double floatValue = 0.0;
    private Long integer = 0L;
    private String lock = "";
    private String stringValue = "";

    public Long getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(Long databaseReference) {
        this.databaseReference = databaseReference;
    }

    public Double getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Double floatValue) {
        this.floatValue = floatValue;
    }

    public Long getInteger() {
        return integer;
    }

    public void setInteger(Long integer) {
        this.integer = integer;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getString() {
        return stringValue;
    }

    public void setString(String string) {
        this.stringValue = string;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }
}
