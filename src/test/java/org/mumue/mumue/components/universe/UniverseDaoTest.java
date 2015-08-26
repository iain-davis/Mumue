package org.mumue.mumue.components.universe;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.acceptance.DatabaseHelper;
import org.mumue.mumue.database.DatabaseAccessor;

import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UniverseDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final UniverseDao dao = new UniverseDao(database);

    @Test
    public void getUniverseNeverReturnsNull() {
        assertNotNull(dao.getUniverse(-1L));
    }

    @Test
    public void getUniverseReturnsUniverse() {
        long universeId = RandomUtils.nextLong(100, 200);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertUniverse(universeId, name, "");

        Universe universe = dao.getUniverse(universeId);

        assertThat(universe.getName(), equalTo(name));
    }

    @Test
    public void getUniversesNeverReturnsNull() {
        assertNotNull(dao.getUniverses());
    }

    @Test
    public void getUniversesReturnsOne() {
        String name = RandomStringUtils.randomAlphabetic(13);
        String description = RandomStringUtils.randomAlphabetic(25);
        insertUniverse(100L, name, description);

        Collection<Universe> universes = dao.getUniverses();

        assertThat(universes.size(), equalTo(1));
        assertThat(universes.stream().findFirst().get().getName(), equalTo(name));
    }

    @Test
    public void getUniversesReturnsThree() {
        String name1 = RandomStringUtils.randomAlphabetic(13);
        String name2 = RandomStringUtils.randomAlphabetic(13);
        String name3 = RandomStringUtils.randomAlphabetic(13);
        String description = RandomStringUtils.randomAlphabetic(25);
        insertUniverse(100L, name1, description);
        insertUniverse(101L, name2, description);
        insertUniverse(102L, name3, description);

        Collection<Universe> universes = dao.getUniverses();

        assertThat(universes.size(), equalTo(3));
    }

    private void insertUniverse(long id, String name, String description) {
        String sql = "insert into universes (id, name, description, created, lastUsed, lastModified, useCount) " +
                "values (" + id + ", '" + name + "', '" + description + "', " +
                "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        database.update(sql);
    }
}
