package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.Link;

public class LinkImporterTest {
    private final LinkImporter importer = new LinkImporter();

    @Test
    public void createComponent() {
        Component component = importer.createComponent();

        assertThat(component, instanceOf(Link.class));
    }
}