package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class GameCharacterDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private GameCharacterDao dao = new GameCharacterDao();

    @Test
    public void getCharacterNeverReturnsNull() {
        assertNotNull(dao.getCharacter(RandomStringUtils.randomAlphabetic(17), GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getCharacterWithNameAndUniverseIdReturnsCharacter() {
        long characterId = RandomUtils.nextLong(200, 300);
        long universeId = RandomUtils.nextLong(100, 200);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(name, universeId);

        assertThat(character.getName(), equalTo(name));
    }

    @Test
    public void getCharacterByNameNeverReturnsNull() {
        assertNotNull(dao.getCharacter(RandomStringUtils.randomAlphabetic(17)));
    }

    @Test
    public void getCharacterByNameReturnsCharacter() {
        long characterId = RandomUtils.nextLong(200, 300);
        long universeId = RandomUtils.nextLong(100, 200);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(name);

        assertThat(character.getName(), equalTo(name));
    }

    private void insertCharacter(long characterId, long universeId, String name) {
        String sql = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId) " +
                "values (" + characterId + ", '" + name + "', '" + RandomStringUtils.randomAlphabetic(16) + "', " +
                "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, 0, "
                + universeId + ");";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
