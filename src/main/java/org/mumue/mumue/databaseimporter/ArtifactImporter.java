package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Artifact;

class ArtifactImporter extends ComponentImporter {
    public Artifact importFrom(List<String> lines) {
        Artifact artifact = new Artifact();
        artifact.setId(getId(lines));
        artifact.setName(getName(lines));
        artifact.setLocationId(getLocationId(lines));
        return artifact;
    }
}
