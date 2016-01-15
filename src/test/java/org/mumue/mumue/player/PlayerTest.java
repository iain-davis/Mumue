package org.mumue.mumue.player;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PlayerTest {
    private final Player player = new Player();

    @Test
    public void administratorDefaultsToFalse() {
        assertFalse(player.isAdministrator());
    }

    @Test
    public void localeDefaultsToEmpty() {
        assertThat(player.getLocale(), equalTo(""));
    }
}
