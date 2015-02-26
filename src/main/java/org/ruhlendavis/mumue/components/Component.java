package org.ruhlendavis.mumue.components;

public abstract class Component extends ComponentBase {
    private String name = "";
    private String description = "";

    @Override
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
