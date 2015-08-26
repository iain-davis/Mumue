package org.mumue.mumue.components.character;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.acceptance.DatabaseHelper;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.importer.GlobalConstants;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class CharacterDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private CharacterDao dao = new CharacterDao(database);

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
    public void getCharacterByIdNeverReturnsNull() {
        assertNotNull(dao.getCharacter(RandomUtils.nextLong(100, 200)));
    }

    @Test
    public void getCharacterByIdReturnsCharacter() {
        long characterId = RandomUtils.nextLong(200, 300);
        long universeId = RandomUtils.nextLong(100, 200);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(characterId);

        assertThat(character.getId(), equalTo(characterId));
    }

    @Test
    public void getCharactersNeverReturnsNull() {
        long playerId = RandomUtils.nextLong(100, 200);
        assertNotNull(dao.getCharacters(playerId));
    }

    @Test
    public void getCharactersByPlayerId() {
        long playerId = RandomUtils.nextLong(100, 200);
        long characterId = RandomUtils.nextLong(200, 300);

        insertCharacter(characterId, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(1));
        assertThat(characters.get(0).getId(), equalTo(characterId));
    }

    @Test
    public void getCharactersByPlayerIdReturnsMultipleCharacters() {
        long playerId = RandomUtils.nextLong(100, 200);
        long characterId1 = RandomUtils.nextLong(200, 300);
        long characterId2 = RandomUtils.nextLong(300, 400);

        insertCharacter(characterId1, playerId);
        insertCharacter(characterId2, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(2));
    }

    @Test
    public void addCharacterAddsCharacter() {
        GameCharacter characterToAdd = new GameCharacter();
        characterToAdd.setId(RandomUtils.nextLong(200, 300));
        characterToAdd.setName(RandomStringUtils.randomAlphabetic(17));
        characterToAdd.setDescription(RandomStringUtils.randomAlphabetic(22));
        characterToAdd.setCreated(Instant.now());
        characterToAdd.setLastModified(Instant.now());
        characterToAdd.setLastUsed(Instant.now());
        characterToAdd.setUseCount(RandomUtils.nextLong(400, 500));
        characterToAdd.setLocationId(RandomUtils.nextLong(500, 600));
        characterToAdd.setUniverseId(RandomUtils.nextLong(600, 700));
        characterToAdd.setPlayerId(RandomUtils.nextLong(700, 800));

        dao.createCharacter(characterToAdd);

        GameCharacter retrieved = dao.getCharacter(characterToAdd.getId());
        assertReflectionEquals(retrieved, characterToAdd);
    }

    private void insertCharacter(long characterId, long playerId) {
        insertCharacter(characterId, RandomUtils.nextLong(100, 200), RandomStringUtils.randomAlphabetic(17), playerId);
    }

    private void insertCharacter(long characterId, long universeId, String name) {
        insertCharacter(characterId, universeId, name, RandomUtils.nextLong(100, 200));
    }

    private void insertCharacter(long characterId, long universeId, String name, long playerId) {
        String sql = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId, playerId) "
                + "values (" + characterId + ", '" + name + "', '" + RandomStringUtils.randomAlphabetic(16) + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, 0, "
                + universeId + ", '" + playerId + "');";
        database.update(sql);
    }
}
