package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.space.Space;

class SpaceImporter implements ComponentImporter {
    @Override
    public Component createComponent() {
        return new Space();
    }
}
