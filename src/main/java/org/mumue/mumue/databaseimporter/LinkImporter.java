package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Link;

class LinkImporter implements ComponentImporter<Link> {

    @Override
    public Link importFrom(List<String> lines) {
        Link link = new Link();
        return link;
    }

}
