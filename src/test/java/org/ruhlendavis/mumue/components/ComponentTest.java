package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ComponentTest {
    Component component = new Component() {
    };

    @Test
    public void componentHasEmptyNameByDefault() {
        assertThat(component.getName(), equalTo(""));
    }

    @Test
    public void componentHasEmptyDescriptionByDefault() {
        assertThat(component.getDescription(), equalTo(""));
    }

    @Test
    public void componentWithNameReturnsComponent() {
        assertThat(component.withName(""), sameInstance(component));
    }

    @Test
    public void withNameSetsName() {
        String name = RandomStringUtils.randomAlphabetic(17);
        component.withName(name);

        assertThat(component.getName(), equalTo(name));
    }
}
