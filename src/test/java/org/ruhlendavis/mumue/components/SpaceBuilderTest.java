package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class SpaceBuilderTest {
    private final SpaceBuilder builder = new SpaceBuilder();

    @Test
    public void buildSetsId() {
        long id = RandomUtils.nextLong(100, 200);

        Space space = builder.withId(id).build();

        assertThat(space.getId(), equalTo(id));
    }

    @Test
    public void buildSetsCreated() {
        Instant created = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Space space = builder.withCreated(created).build();

        assertThat(space.getCreated(), equalTo(created));
    }

    @Test
    public void buildSetsModified() {
        Instant modified = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Space space = builder.withLastModified(modified).build();

        assertThat(space.getLastModified(), equalTo(modified));
    }

    @Test
    public void buildSetsUsed() {
        Instant lastUsed = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Space space = builder.withLastUsed(lastUsed).build();

        assertThat(space.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void buildSetsUseCount() {
        long useCount = RandomUtils.nextLong(100, 200);

        Space space = builder.withUseCount(useCount).build();

        assertThat(space.getUseCount(), equalTo(useCount));
    }

    @Test
    public void buildSetsName() {
        String name = RandomStringUtils.randomAlphabetic(17);

        Space space = builder.withName(name).build();

        assertThat(space.getName(), equalTo(name));
    }

    @Test
    public void buildSetsDescription() {
        String description = RandomStringUtils.randomAlphabetic(17);

        Space space = builder.withDescription(description).build();

        assertThat(space.getDescription(), equalTo(description));
    }

    @Test
    public void buildSetsLocationId() {
        long locationId = RandomUtils.nextLong(300, 400);

        Space space = builder.withLocationId(locationId).build();

        assertThat(space.getLocationId(), equalTo(locationId));
    }

    @Test
    public void buildSetsUniverseId() {
        long universeId = RandomUtils.nextLong(300, 400);

        Space space = builder.withUniverseId(universeId).build();

        assertThat(space.getUniverseId(), equalTo(universeId));
    }
}
