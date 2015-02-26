package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class GameCharacterTest {
    private final GameCharacter character = new GameCharacter();

    @Test
    public void playerIdDefaultsToEmptyString() {
        assertThat(character.getPlayerId(), equalTo(""));
    }

    @Test
    public void withNameReturnsSameInstance() {
        assertThat(character.withName(""), sameInstance(character));
    }

    @Test
    public void withNameSetsName() {
        String name = RandomStringUtils.randomAlphabetic(17);

        character.withName(name);

        assertThat(character.getName(), equalTo(name));
    }
}
