package org.ruhlendavis.mumue.player;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PlayerTest {
    private final Player player = new Player();

    @Test
    public void administratorDefaultsToFalse() {
        assertFalse(player.isAdministrator());
    }
}
