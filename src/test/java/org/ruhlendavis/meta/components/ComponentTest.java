package org.ruhlendavis.meta.components;

import org.junit.Assert;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;

public class ComponentTest {
    Component component = new Component();

    @Test
    public void componentDefaultId() {
        Assert.assertEquals(GlobalConstants.REFERENCE_UNKNOWN, component.getId(), 0);
    }

    @Test
    public void componentDefaultOwnerId() {
        Assert.assertEquals(GlobalConstants.REFERENCE_UNKNOWN, component.getOwner().getId(), 0);
    }
}
