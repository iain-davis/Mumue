package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Link;

class LinkImporter extends ComponentImporter {
    public Link importFrom(List<String> lines) {
        Link link = new Link();
        link.setId(getId(lines));
        link.setName(getName(lines));
        link.setLocationId(getLocationId(lines));
        return link;
    }
}
