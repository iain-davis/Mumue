package org.mumue.mumue.importer.components.properties;

import org.junit.Assert;
import org.junit.Test;

public class LockPropertyTest {
    LockProperty property = new LockProperty();

    @Test
    public void lockPropertyDefaultValue() {
        Assert.assertEquals("", property.getValue());
    }
}
