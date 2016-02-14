package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.space.Space;

class SpaceImporter extends ComponentImporter {
    public Space importFrom(List<String> lines) {
        Space space = new Space();
        space.setId(getId(lines));
        space.setName(getName(lines));
        space.setLocationId(getLocationId(lines));
        return space;
    }
}
