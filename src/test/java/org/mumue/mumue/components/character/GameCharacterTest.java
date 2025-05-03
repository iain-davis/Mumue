package org.mumue.mumue.components.character;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.mumue.mumue.importer.GlobalConstants;

public class GameCharacterTest {
    private final GameCharacter character = new GameCharacter();

    @Test
    public void playerIdDefaultsToUnknown() {
        assertThat(character.getPlayerId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }
}
