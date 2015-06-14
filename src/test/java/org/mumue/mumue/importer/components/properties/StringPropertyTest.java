package org.mumue.mumue.importer.components.properties;

import org.junit.Assert;
import org.junit.Test;

public class StringPropertyTest {
    StringProperty property = new StringProperty();

    @Test
    public void stringPropertyDefaultValue() {
        Assert.assertEquals("", property.getValue());
    }
}
