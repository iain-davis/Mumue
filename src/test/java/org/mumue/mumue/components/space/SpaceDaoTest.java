package org.mumue.mumue.components.space;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

public class SpaceDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final SpaceDao dao = new SpaceDao(database);

    @Test
    public void getUniverseNeverReturnsNull() {
        assertNotNull(dao.getSpace(-1L));
    }

    @Test
    public void getUniverseReturnsUniverse() {
        long spaceId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertSpace(spaceId, name);

        Space space = dao.getSpace(spaceId);

        assertThat(space.getName(), equalTo(name));
    }

    private void insertSpace(long spaceId, String name) {
        String sql = "insert into spaces (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId) " +
                "values (" + spaceId + ", '" + name + "', '" + RandomStringUtils.insecure().nextAlphabetic(5) + "', " +
                "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, -1, -1);";
        database.update(sql);
    }
}
