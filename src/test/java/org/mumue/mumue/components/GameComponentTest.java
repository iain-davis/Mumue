package org.mumue.mumue.components;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GameComponentTest {
    private final GameComponent component = new GameComponent() {};

    @Test
    void componentHasEmptyNameByDefault() {
        String name = component.getName();

        assertThat(name, equalTo(""));
    }

    @Test
    void componentHasEmptyDescriptionByDefault() {
        String description = component.getDescription();

        assertThat(description, equalTo(""));
    }
}
