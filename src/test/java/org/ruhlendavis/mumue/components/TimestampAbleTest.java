package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class TimestampAbleTest {
    private final TimestampAble timestampAble = new TimestampAble() {
    };

    @Test
    public void componentHasUnknownDefaultId() {
        assertThat(timestampAble.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void createdHasDefault() {
        assertNotNull(timestampAble.getCreated());
    }

    @Test
    public void modifiedHasDefault() {
        assertNotNull(timestampAble.getLastModified());
    }

    @Test
    public void usedHasDefault() {
        assertNotNull(timestampAble.getLastUsed());
    }

    @Test
    public void useCountDefaultsToZero() {
        assertThat(timestampAble.getUseCount(), equalTo(0L));
    }
}
