package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.space.Space;

class SpaceImporter implements ComponentImporter<Space> {

    @Override
    public Space importFrom(List<String> lines) {
        Space space = new Space();
        return space;
    }
}
