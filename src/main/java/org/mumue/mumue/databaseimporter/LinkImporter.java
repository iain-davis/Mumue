package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Link;
import org.mumue.mumue.components.universe.Universe;

class LinkImporter extends ComponentImporter {
    public Link importFrom(List<String> lines, Universe universe) {
        Link link = new Link();
        link.setId(getId(lines));
        link.setName(getName(lines));
        link.setLocationId(getLocationId(lines));
        link.setUniverseId(universe.getId());
        return link;
    }
}
