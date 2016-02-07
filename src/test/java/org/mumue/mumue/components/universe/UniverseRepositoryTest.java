package org.mumue.mumue.components.universe;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ComponentIdManager;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

public class UniverseRepositoryTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final UniverseRepository dao = new UniverseRepository(mock(ComponentIdManager.class), database);

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

    @Test
    public void saveUniverseSavesToDatabase() {
        Universe expected = new UniverseBuilder().withName(RandomStringUtils.randomAlphabetic(13)).build();

        dao.save(expected);

        Universe universe = getUniverse(expected.getName());
        assertThat(universe, notNullValue());
    }

    @Test
    public void insertOnlyIfIdIsUnknown() {
        Universe expected = new UniverseBuilder().withName(RandomStringUtils.randomAlphabetic(13)).build();

        dao.save(expected);
        dao.save(expected);

        Universe universe = getUniverse(expected.getName());
        assertThat(universe, notNullValue());
    }

    @Test
    public void updateOnSubsequentCall() {
        Universe expected = new UniverseBuilder().withName(RandomStringUtils.randomAlphabetic(13)).build();

        dao.save(expected);
        expected.setName(RandomStringUtils.randomAlphabetic(14));
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
