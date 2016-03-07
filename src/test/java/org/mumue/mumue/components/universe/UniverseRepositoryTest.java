package org.mumue.mumue.components.universe;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.testobjectbuilder.Nimue;

import java.util.Collection;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

class UniverseRepositoryTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final UniverseRepository repository = new UniverseRepository(database);

    @Test
    public void getUniverseNeverReturnsNull() {
        assertNotNull(repository.getUniverse(-1L));
    }

    @Test
    void getUniverseReturnsUniverse() {
        long universeId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertUniverse(universeId, name, "");

        Universe universe = repository.getUniverse(universeId);

        assertThat(universe.getName(), equalTo(name));
    }

    @Test
    public void getUniversesNeverReturnsNull() {
        assertNotNull(repository.getUniverses());
    }

    @Test
    void getUniversesReturnsOne() {
        String name = RandomStringUtils.insecure().nextAlphabetic(13);
        String description = RandomStringUtils.insecure().nextAlphabetic(25);
        insertUniverse(100L, name, description);

        Collection<Universe> universes = repository.getUniverses();

        assertThat(universes.size(), equalTo(1));
        assertThat(universes.stream().findFirst().get().getName(), equalTo(name));
    }

    @Test
    void getUniversesReturnsThree() {
        String name1 = RandomStringUtils.insecure().nextAlphabetic(13);
        String name2 = RandomStringUtils.insecure().nextAlphabetic(13);
        String name3 = RandomStringUtils.insecure().nextAlphabetic(13);
        String description = RandomStringUtils.insecure().nextAlphabetic(25);
        insertUniverse(100L, name1, description);
        insertUniverse(101L, name2, description);
        insertUniverse(102L, name3, description);

        Collection<Universe> universes = repository.getUniverses();

        assertThat(universes.size(), equalTo(3));
    }

    @Test
    public void addUniverseInsertsIntoDatabase() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
    }

    @Test
    public void addUniverseIncludesName() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withName(RandomStringUtils.randomAlphabetic(13))
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getName(), equalTo(expected.getName()));
    }

    @Test
    public void addUniverseIncludesDescription() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withDescription(RandomStringUtils.randomAlphabetic(25))
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getDescription(), equalTo(expected.getDescription()));
    }

    @Test
    public void addUniverseIncludesStartingSpace() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withStartingSpaceId(new Random().nextInt(1000) + 1)
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getStartingSpaceId(), equalTo(expected.getStartingSpaceId()));
    }

    @Test
    public void addUniverseIncludesUseCount() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withUseCount(new Random().nextInt(33333) + 1)
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getUseCount(), equalTo(expected.getUseCount()));
    }

    @Test
    public void addUniverseIncludesCreatedTimestamp() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withCreated(Nimue.randomInstant())
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getCreated(), equalTo(expected.getCreated()));
    }

    @Test
    public void addUniverseIncludesLastUsedTimestamp() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withLastUsed(Nimue.randomInstant())
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getLastUsed(), equalTo(expected.getLastUsed()));
    }

    @Test
    public void addUniverseIncludesLastModifiedTimestamp() {
        Universe expected = new UniverseBuilder()
                .withId(new Random().nextInt(1000) + 1)
                .withLastModified(Nimue.randomInstant())
                .build();

        repository.add(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
        assertThat(universe.getLastModified(), equalTo(expected.getLastModified()));
    }

    @Test
    public void addOnlyWithRealId() {
        Universe expected = new UniverseBuilder().withId(GlobalConstants.REFERENCE_UNKNOWN).withName(RandomStringUtils.insecure().nextAlphabetic(13)).build();

        assertThrows(RuntimeException.class, () -> repository.add(expected));
    }

    @Test
    public void saveUniverse() {
        Universe expected = new UniverseBuilder().withId(new Random().nextInt(1000) + 1).withName(RandomStringUtils.randomAlphabetic(13)).build();

        repository.add(expected);
        expected.setName(RandomStringUtils.randomAlphabetic(14));
        repository.save(expected);

        Universe universe = repository.getUniverse(expected.getId());
        assertThat(universe, notNullValue());
    }

    private void insertUniverse(long id, String name, String description) {
        String sql = "insert into universes (id, name, description, created, lastUsed, lastModified, useCount) " +
                "values (" + id + ", '" + name + "', '" + description + "', " +
                "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        database.update(sql);
    }
}
