package org.mumue.mumue.importer.components;

public interface Ownable {
    Component getOwner();
    void setOwner(Component owner);
}
