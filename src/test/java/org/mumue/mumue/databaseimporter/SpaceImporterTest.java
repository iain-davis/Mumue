package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.space.Space;

public class SpaceImporterTest {
    private final SpaceImporter importer = new SpaceImporter();

    @Test
    public void createComponent() {
        Component component = importer.createComponent();

        assertThat(component, instanceOf(Space.class));
    }
}