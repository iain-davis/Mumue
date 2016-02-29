package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Artifact;

class ArtifactImporter implements ComponentImporter<Artifact> {
    @Override
    public Artifact importFrom(List<String> lines) {
        Artifact artifact = new Artifact();
        return artifact;
    }
}
