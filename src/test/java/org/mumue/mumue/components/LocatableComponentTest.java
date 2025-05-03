package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mumue.mumue.importer.GlobalConstants;

public class LocatableComponentTest {
    private final LocatableComponent component = new LocatableComponent() {
    };

    @Test
    public void componentHasUnknownDefaultLocationId() {
        assertThat(component.getLocationId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void componentHasUnknownDefaultUniverseId() {
        assertThat(component.getUniverseId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
