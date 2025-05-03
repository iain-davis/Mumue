package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mumue.mumue.importer.GlobalConstants;

public class GameComponentTest {
    private final GameComponent component = new GameComponent() {};

    @Test
    public void componentHasEmptyNameByDefault() {
        assertThat(component.getName(), equalTo(""));
    }

    @Test
    public void componentHasEmptyDescriptionByDefault() {
        assertThat(component.getDescription(), equalTo(""));
    }
}
