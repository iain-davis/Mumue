package org.ruhlendavis.meta.componentsold;

import org.ruhlendavis.meta.components.Component;

public interface Ownable {
    public Component getOwner();

    public void setOwner(Component owner);
}
