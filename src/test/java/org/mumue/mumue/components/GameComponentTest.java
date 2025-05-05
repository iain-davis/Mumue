package org.mumue.mumue.components;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GameComponentTest {
    private final GameComponent component = new GameComponent() {};

    @Test
    void componentHasEmptyNameByDefault() {
        assertThat(component.getName(), equalTo(""));
    }

    @Test
    void componentHasEmptyDescriptionByDefault() {
        assertThat(component.getDescription(), equalTo(""));
    }
}
