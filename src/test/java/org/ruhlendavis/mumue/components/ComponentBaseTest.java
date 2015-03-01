package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class ComponentBaseTest {
    private final ComponentBase componentBase = new ComponentBase() {
    };

    @Test
    public void componentHasUnknownDefaultId() {
        assertThat(componentBase.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void createdHasDefault() {
        assertNotNull(componentBase.getCreated());
    }

    @Test
    public void modifiedHasDefault() {
        assertNotNull(componentBase.getLastModified());
    }

    @Test
    public void usedHasDefault() {
        assertNotNull(componentBase.getLastUsed());
    }

    @Test
    public void useCountDefaultsToZero() {
        assertThat(componentBase.getUseCount(), equalTo(0L));
    }
}
