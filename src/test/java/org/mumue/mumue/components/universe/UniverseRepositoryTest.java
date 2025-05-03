package org.mumue.mumue.components.universe;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.importer.GlobalConstants;

import java.util.Collection;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

class UniverseRepositoryTest {
    private static final Random RANDOM = new Random();
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final UniverseRepository dao = new UniverseRepository(database);

    @Test
    void getUniverseNeverReturnsNull() {
        assertNotNull(dao.getUniverse(-1L));
    }

    @Test
    void getUniverseReturnsUniverse() {
        long universeId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertUniverse(universeId, name, "");

        Universe universe = dao.getUniverse(universeId);

        assertThat(universe.getName(), equalTo(name));
    }

    @Test
    void getUniversesNeverReturnsNull() {
        assertNotNull(dao.getUniverses());
    }

    @Test
    void getUniversesReturnsOne() {
        String name = RandomStringUtils.insecure().nextAlphabetic(13);
        String description = RandomStringUtils.insecure().nextAlphabetic(25);
        insertUniverse(100L, name, description);

        Collection<Universe> universes = dao.getUniverses();

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

        Collection<Universe> universes = dao.getUniverses();

        assertThat(universes.size(), equalTo(3));
    }

    @Test
    void addUniverseInsertsIntoDatabase() {
        Universe expected = new UniverseBuilder().withId(RANDOM.nextInt(1000) + 1).withName(RandomStringUtils.insecure().nextAlphabetic(13)).build();

        dao.add(expected);

        Universe universe = getUniverse(expected.getName());
        assertThat(universe, notNullValue());
    }

    @Test
    void addOnlyWithRealId() {
        Universe expected = new UniverseBuilder().withId(GlobalConstants.REFERENCE_UNKNOWN).withName(RandomStringUtils.insecure().nextAlphabetic(13)).build();

        assertThrows(RuntimeException.class, () -> dao.add(expected));
    }

    @Test
    void saveUniverse() {
        Universe expected = new UniverseBuilder().withId(RANDOM.nextInt(1000) + 1).withName(RandomStringUtils.insecure().nextAlphabetic(13)).build();

        dao.add(expected);
        expected.setName(RandomStringUtils.insecure().nextAlphabetic(14));
        dao.save(expected);

        Universe universe = getUniverse(expected.getName());
        assertThat(universe, notNullValue());
    }

    private Universe getUniverse(String name) {
        ResultSetHandler<Universe> resultSetHandler = new BeanHandler<>(Universe.class, new UniverseRowProcessor());
        return database.query("select * from universes where name = ?", resultSetHandler, name);
    }

    private void insertUniverse(long id, String name, String description) {
        String sql = "insert into universes (id, name, description, created, lastUsed, lastModified, useCount) " +
                "values (" + id + ", '" + name + "', '" + description + "', " +
                "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        database.update(sql);
    }
}
