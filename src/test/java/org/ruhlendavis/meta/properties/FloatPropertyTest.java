package org.ruhlendavis.meta.properties;

import org.junit.Assert;
import org.junit.Test;

public class FloatPropertyTest {
    FloatProperty property = new FloatProperty();

    @Test
    public void floatPropertyDefaultValue() {
        Assert.assertEquals(0.0, property.getValue(), 0);
    }
}
