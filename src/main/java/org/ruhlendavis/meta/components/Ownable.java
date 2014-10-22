package org.ruhlendavis.meta.components;

public interface Ownable {
    public Component getOwner();

    public void setOwner(Component owner);
}
