package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.universe.Universe;

class ArtifactImporter extends ComponentImporter {
    public Artifact importFrom(List<String> lines, Universe universe) {
        Artifact artifact = new Artifact();
        artifact.setId(getId(lines));
        artifact.setName(getName(lines));
        artifact.setLocationId(getLocationId(lines));
        artifact.setUniverseId(universe.getId());
        return artifact;
    }
}
