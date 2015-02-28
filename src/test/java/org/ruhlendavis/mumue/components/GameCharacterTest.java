package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class GameCharacterTest {
    private final GameCharacter character = new GameCharacter();

    @Test
    public void playerIdDefaultsToUnknown() {
        assertThat(character.getPlayerId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
