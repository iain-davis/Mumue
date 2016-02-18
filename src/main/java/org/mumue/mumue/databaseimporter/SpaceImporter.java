package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.universe.Universe;

class SpaceImporter extends ComponentImporter {
    public Space importFrom(List<String> lines, Universe universe) {
        Space space = new Space();
        space.setId(getId(lines));
        space.setName(getName(lines));
        space.setLocationId(getLocationId(lines));
        space.setUniverseId(universe.getId());
        return space;
    }
}
