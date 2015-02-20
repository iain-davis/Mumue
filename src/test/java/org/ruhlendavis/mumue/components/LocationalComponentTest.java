package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class LocationalComponentTest {
    private final LocationalComponent component = new LocationalComponent() {};

    @Test
    public void componentHasUnknownDefaultLocationId() {
        assertThat(component.getLocationId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void componentHasUnknownDefaultUniverseId() {
        assertThat(component.getUniverseId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
