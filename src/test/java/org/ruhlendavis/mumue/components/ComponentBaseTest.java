package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomUtils;
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
    public void withIdReturnsSameInstance() {
        assertThat(componentBase.withId(0L), sameInstance(componentBase));
    }

    @Test
    public void withIdSetsId() {
        long id = RandomUtils.nextLong(200, 300);

        componentBase.withId(id);

        assertThat(componentBase.getId(), equalTo(id));
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
