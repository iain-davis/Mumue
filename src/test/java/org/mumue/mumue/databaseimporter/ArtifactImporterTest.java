package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.Component;

public class ArtifactImporterTest {
    private final ArtifactImporter importer = new ArtifactImporter();

    @Test
    public void createComponent() {
        Component component = importer.createComponent();

        assertThat(component, instanceOf(Artifact.class));
    }
}