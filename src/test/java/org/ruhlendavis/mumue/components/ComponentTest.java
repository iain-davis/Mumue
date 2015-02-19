package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

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