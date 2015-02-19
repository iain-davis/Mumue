package org.ruhlendavis.meta.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.meta.importer.GlobalConstants;

public class PlayerTest {
    private final Player player = new Player();

    @Test
    public void locationIdDefaultsToUnknown() {
        assertThat(player.getLocationId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
