package org.ruhlendavis.meta.properties;

import org.junit.Assert;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;

public class ReferencePropertyTest {
    ReferenceProperty property = new ReferenceProperty();
    @Test
    public void referencePropertyDefaultValue() {
        Assert.assertEquals(GlobalConstants.REFERENCE_UNKNOWN, property.getValue(), 0);
    }
}
