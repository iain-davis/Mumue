package org.ruhlendavis.meta.components;

import org.junit.Assert;
import org.junit.Test;

import org.ruhlendavis.meta.importer.GlobalConstants;

public class IdableTest {
    IdAble component = new IdAble() {
    };

    @Test
    public void idAbleHasUnknownDefaultId() {
        Assert.assertEquals(GlobalConstants.REFERENCE_UNKNOWN, component.getId(), 0);
    }
}
