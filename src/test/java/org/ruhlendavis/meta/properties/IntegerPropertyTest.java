package org.ruhlendavis.meta.properties;

import org.junit.Assert;
import org.junit.Test;

public class IntegerPropertyTest {
    IntegerProperty property = new IntegerProperty();

    @Test
    public void integerPropertyDefaultValue() {
        Assert.assertEquals(0, property.getValue(), 0);
    }
}
