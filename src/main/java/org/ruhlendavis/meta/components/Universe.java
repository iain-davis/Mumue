package org.ruhlendavis.meta.components;

public class Universe {
    private int id = 0;
    private String name = "";
    private UniverseType type = UniverseType.NONE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UniverseType getType() {
        return type;
    }

    public void setType(UniverseType type) {
        this.type = type;
    }
}
