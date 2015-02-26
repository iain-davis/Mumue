package org.ruhlendavis.mumue.components;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public abstract class Component extends TimestampAble {
    private Long id = GlobalConstants.REFERENCE_UNKNOWN;
    private String name = "";
    private String description = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Component withId(Long id) {
        setId(id);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Component withName(String name) {
        setName(name);
        return this;
    }
}
