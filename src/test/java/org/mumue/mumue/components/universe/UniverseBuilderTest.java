package org.mumue.mumue.components.universe;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class UniverseBuilderTest {
    private final UniverseBuilder builder = new UniverseBuilder();

    @Test
    public void buildSetsId() {
        long id = RandomUtils.insecure().randomLong(100, 200);

        Universe universe = builder.withId(id).build();

        assertThat(universe.getId(), equalTo(id));
    }

    @Test
    public void buildSetsCreated() {
        Instant created = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        Universe universe = builder.withCreated(created).build();

        assertThat(universe.getCreated(), equalTo(created));
    }

    @Test
    public void buildSetsModified() {
        Instant modified = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        Universe universe = builder.withLastModified(modified).build();

        assertThat(universe.getLastModified(), equalTo(modified));
    }

    @Test
    public void buildSetsUsed() {
        Instant lastUsed = Instant.now().minusSeconds(RandomUtils.insecure().randomLong(100, 200));

        Universe universe = builder.withLastUsed(lastUsed).build();

        assertThat(universe.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void buildSetsUseCount() {
        long useCount = RandomUtils.insecure().randomLong(100, 200);

        Universe universe = builder.withUseCount(useCount).build();

        assertThat(universe.getUseCount(), equalTo(useCount));
    }

    @Test
    public void buildSetsName() {
        String name = RandomStringUtils.insecure().nextAlphabetic(17);

        Universe universe = builder.withName(name).build();

        assertThat(universe.getName(), equalTo(name));
    }

    @Test
    public void buildSetsDescription() {
        String description = RandomStringUtils.insecure().nextAlphabetic(17);

        Universe universe = builder.withDescription(description).build();

        assertThat(universe.getDescription(), equalTo(description));
    }

    @Test
    public void buildSetsStartingSpaceId() {
        long startingSpaceId = RandomUtils.insecure().randomLong(100, 200);

        Universe universe = builder.withStartingSpaceId(startingSpaceId).build();

        assertThat(universe.getStartingSpaceId(), equalTo(startingSpaceId));
    }

}
