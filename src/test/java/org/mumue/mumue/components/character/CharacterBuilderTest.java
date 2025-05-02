package org.mumue.mumue.components.character;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class CharacterBuilderTest {
    private final CharacterBuilder builder = new CharacterBuilder();

    @Test
    public void buildSetsId() {
        long id = RandomUtils.insecure().randomLong(100, 200);

        GameCharacter character = builder.withId(id).build();

        assertThat(character.getId(), equalTo(id));
    }

    @Test
    public void buildSetsCreated() {
        Instant created = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        GameCharacter character = builder.withCreated(created).build();

        assertThat(character.getCreated(), equalTo(created));
    }

    @Test
    public void buildSetsModified() {
        Instant modified = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        GameCharacter character = builder.withLastModified(modified).build();

        assertThat(character.getLastModified(), equalTo(modified));
    }

    @Test
    public void buildSetsUsed() {
        Instant lastUsed = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        GameCharacter character = builder.withLastUsed(lastUsed).build();

        assertThat(character.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void buildSetsUseCount() {
        long useCount = RandomUtils.insecure().randomLong(100, 200);

        GameCharacter character = builder.withUseCount(useCount).build();

        assertThat(character.getUseCount(), equalTo(useCount));
    }

    @Test
    public void buildSetsName() {
        String name = RandomStringUtils.insecure().nextAlphabetic(17);

        GameCharacter character = builder.withName(name).build();

        assertThat(character.getName(), equalTo(name));
    }

    @Test
    public void buildSetsDescription() {
        String description = RandomStringUtils.insecure().nextAlphabetic(17);

        GameCharacter character = builder.withDescription(description).build();

        assertThat(character.getDescription(), equalTo(description));
    }

    @Test
    public void buildSetsLocationId() {
        long locationId = RandomUtils.insecure().randomLong(300, 400);

        GameCharacter character = builder.withLocationId(locationId).build();

        assertThat(character.getLocationId(), equalTo(locationId));
    }

    @Test
    public void buildSetsHomeLocationId() {
        long locationId = RandomUtils.insecure().randomLong(300, 400);

        GameCharacter character = builder.withHomeLocationId(locationId).build();

        assertThat(character.getHomeLocationId(), equalTo(locationId));
    }

    @Test
    public void buildSetsUniverseId() {
        long universeId = RandomUtils.insecure().randomLong(300, 400);

        GameCharacter character = builder.withUniverseId(universeId).build();

        assertThat(character.getUniverseId(), equalTo(universeId));
    }

    @Test
    public void buildSetsPlayerId() {
        long playerId = RandomUtils.insecure().randomLong(300, 400);

        GameCharacter character = builder.withPlayerId(playerId).build();

        assertThat(character.getPlayerId(), equalTo(playerId));
    }
}
