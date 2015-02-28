package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class GameCharacterTest {
    private final GameCharacter character = new GameCharacter();

    @Test
    public void playerIdDefaultsToUnknown() {
        assertThat(character.getPlayerId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
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

    @Test
    public void withLocationIdReturnsSameInstance() {
        assertThat(character.withLocationId(0L), sameInstance(character));
    }

    @Test
    public void withLocationIdSetsLocationId() {
        long locationId = RandomUtils.nextLong(100, 200);

        character.withLocationId(locationId);

        assertThat(character.getLocationId(), equalTo(locationId));
    }

    @Test
    public void withUniverseIdReturnsSameInstance() {
        assertThat(character.withUniverseId(0L), sameInstance(character));
    }

    @Test
    public void withUniverseIdSetsLocationId() {
        long universeId = RandomUtils.nextLong(100, 200);

        character.withUniverseId(universeId);

        assertThat(character.getUniverseId(), equalTo(universeId));
    }
}
