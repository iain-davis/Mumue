package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GameCharacterTest {
    private final GameCharacter character = new GameCharacter();

    @Test
    public void playerIdDefaultsToEmptyString() {
        assertThat(character.getPlayerId(), equalTo(""));
    }
}
