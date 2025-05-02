package org.mumue.mumue.importer.components.properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

public class PropertyTreeTest {
    PropertyTree propertyTree = new PropertyTree();

    @Test
    public void getPropertyReturnsProperty() {
        String path = RandomStringUtils.insecure().nextAlphabetic(7);
        Property property = new Property();
        propertyTree.setProperty(path, property);
        Assert.assertSame(property, propertyTree.getProperty(path));
    }

    @Test
    public void getPropertyWithSameLeafInDifferentDirectories() {
        String leafName = RandomStringUtils.insecure().nextAlphabetic(8);

        String path1 = RandomStringUtils.insecure().nextAlphabetic(7) + "/" + leafName;
        Property property1 = new Property();
        propertyTree.setProperty(path1, property1);

        String path2 = RandomStringUtils.insecure().nextAlphabetic(6) + "/" + leafName;
        Property property2 = new Property();
        propertyTree.setProperty(path2, property2);

        Assert.assertSame(property1, propertyTree.getProperty(path1));
        Assert.assertSame(property2, propertyTree.getProperty(path2));

    }
}
