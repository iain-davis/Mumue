package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.mumue.mumue.importer.GlobalConstants;

public class ComponentTest {
    private final Component component = new Component() {
    };

    @Test
    public void componentHasUnknownDefaultId() {
        assertThat(component.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void createdHasDefault() {
        assertNotNull(component.getCreated());
    }

    @Test
    public void modifiedHasDefault() {
        assertNotNull(component.getLastModified());
    }

    @Test
    public void usedHasDefault() {
        assertNotNull(component.getLastUsed());
    }

    @Test
    public void useCountDefaultsToZero() {
        assertThat(component.getUseCount(), equalTo(0L));
    }
}
