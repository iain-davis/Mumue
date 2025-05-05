package org.mumue.mumue.components;

import org.junit.jupiter.api.Test;
import org.mumue.mumue.importer.GlobalConstants;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class ComponentTest {
    private final Component component = new Component() {};

    @Test
    void componentHasUnknownDefaultId() {
        assertThat(component.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    void createdHasDefault() {
        Instant created = component.getCreated();
        assertThat(created, notNullValue());
    }

    @Test
    void modifiedHasDefault() {
        Instant lastModified = component.getLastModified();
        assertThat(lastModified, notNullValue());
    }

    @Test
    void usedHasDefault() {
        Instant lastUsed = component.getLastUsed();
        assertThat(lastUsed, notNullValue());
    }

    @Test
    void useCountDefaultsToZero() {
        long useCount = component.getUseCount();

        assertThat(useCount, equalTo(0L));
    }
}
