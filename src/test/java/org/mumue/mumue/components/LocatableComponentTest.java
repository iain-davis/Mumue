package org.mumue.mumue.components;

import org.junit.jupiter.api.Test;
import org.mumue.mumue.importer.GlobalConstants;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class LocatableComponentTest {
    private final LocatableComponent component = new LocatableComponent() {};

    @Test
    void componentHasUnknownDefaultLocationId() {
        assertThat(component.getLocationId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    void componentHasUnknownDefaultUniverseId() {
        assertThat(component.getUniverseId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
