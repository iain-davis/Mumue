package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.Component;

class ArtifactImporter implements ComponentImporter {
    @Override
    public Component createComponent() {
        return new Artifact();
    }
}
