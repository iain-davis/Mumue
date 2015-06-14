package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

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
}
