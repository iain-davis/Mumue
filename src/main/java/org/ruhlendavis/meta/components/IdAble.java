package org.ruhlendavis.meta.components;

import org.ruhlendavis.meta.importer.GlobalConstants;

public abstract class IdAble {
    private Long id = GlobalConstants.REFERENCE_UNKNOWN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IdAble withId(Long id) {
        setId(id);
        return this;
    }
}
