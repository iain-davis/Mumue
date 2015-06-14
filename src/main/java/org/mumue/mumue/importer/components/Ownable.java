package org.mumue.mumue.importer.components;

public interface Ownable {
    public Component getOwner();

    public void setOwner(Component owner);
}
