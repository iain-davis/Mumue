package org.ruhlendavis.meta.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import org.ruhlendavis.meta.importer.GlobalConstants;

public class ComponentTest {
    Component component = new Component();

    @Test
    public void componentHasUnknownDefaultId() {
        assertThat(component.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void componentHasUnknownDefaultLocationId() {
        assertThat(component.getLocationId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
