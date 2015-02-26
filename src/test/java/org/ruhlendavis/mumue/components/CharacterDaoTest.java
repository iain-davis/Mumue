package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class CharacterDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private CharacterDao dao = new CharacterDao();

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

    @Test
    public void getCharactersNeverReturnsNull() {
        assertNotNull(dao.getCharacters(RandomStringUtils.randomAlphabetic(9)));
    }

    @Test
    public void getCharactersByPlayerId() {
        String playerId = RandomStringUtils.randomAlphabetic(7);
        long characterId = RandomUtils.nextLong(200, 300);

        insertCharacter(characterId, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(1));
        assertThat(characters.get(0).getId(), equalTo(characterId));
    }

    @Test
    public void getCharactersByPlayerIdReturnsMultipleCharacters() {
        String playerId = RandomStringUtils.randomAlphabetic(7);
        long characterId1 = RandomUtils.nextLong(200, 300);
        long characterId2 = RandomUtils.nextLong(200, 300);

        insertCharacter(characterId1, playerId);
        insertCharacter(characterId2, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(2));
    }

    private void insertCharacter(long characterId, String playerId) {
        insertCharacter(characterId, RandomUtils.nextLong(100, 200), RandomStringUtils.randomAlphabetic(17), playerId);
    }

    private void insertCharacter(long characterId, long universeId, String name) {
        insertCharacter(characterId, universeId, name, RandomStringUtils.randomAlphabetic(7));
    }

    private void insertCharacter(long characterId, long universeId, String name, String playerId) {
        String sql = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId, playerId) "
                + "values (" + characterId + ", '" + name + "', '" + RandomStringUtils.randomAlphabetic(16) + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, 0, "
                + universeId + ", '" + playerId + "');";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
