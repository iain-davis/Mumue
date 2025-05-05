package org.mumue.mumue.components.character;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.importer.GlobalConstants;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class CharacterDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final CharacterDao dao = new CharacterDao(database);

    @Test
    void getCharacterNeverReturnsNull() {
        GameCharacter character = dao.getCharacter(RandomStringUtils.insecure().nextAlphabetic(17), GlobalConstants.REFERENCE_UNKNOWN);

        assertThat(character, notNullValue());
    }

    @Test
    void getCharacterWithNameAndUniverseIdReturnsCharacter() {
        long characterId = RandomUtils.insecure().randomLong(100, 200);
        long universeId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(name, universeId);

        assertThat(character.getName(), equalTo(name));
    }

    @Test
    void getCharacterByNameNeverReturnsNull() {
        GameCharacter character = dao.getCharacter(RandomStringUtils.insecure().nextAlphabetic(17));

        assertThat(character, notNullValue());
    }

    @Test
    void getCharacterByNameReturnsCharacter() {
        long characterId = RandomUtils.insecure().randomLong(200, 300);
        long universeId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(name);

        assertThat(character.getName(), equalTo(name));
    }

    @Test
    void getCharacterByIdNeverReturnsNull() {
        GameCharacter character = dao.getCharacter(RandomUtils.insecure().randomLong(100, 200));

        assertThat(character, notNullValue());
    }

    @Test
    void getCharacterByIdReturnsCharacter() {
        long characterId = RandomUtils.insecure().randomLong(200, 300);
        long universeId = RandomUtils.insecure().randomLong(100, 200);
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        insertCharacter(characterId, universeId, name);

        GameCharacter character = dao.getCharacter(characterId);

        assertThat(character.getId(), equalTo(characterId));
    }

    @Test
    void getCharactersNeverReturnsNull() {
        long playerId = RandomUtils.insecure().randomLong(100, 200);

        List<GameCharacter> characters = dao.getCharacters(playerId);

        assertThat(characters, notNullValue());
    }

    @Test
    void getCharactersByPlayerId() {
        long playerId = RandomUtils.insecure().randomLong(100, 200);
        long characterId = RandomUtils.insecure().randomLong(200, 300);

        insertCharacter(characterId, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(1));
        assertThat(characters.get(0).getId(), equalTo(characterId));
    }

    @Test
    void getCharactersByPlayerIdReturnsMultipleCharacters() {
        long playerId = RandomUtils.insecure().randomLong(100, 200);
        long characterId1 = RandomUtils.insecure().randomLong(200, 300);
        long characterId2 = RandomUtils.insecure().randomLong(300, 400);

        insertCharacter(characterId1, playerId);
        insertCharacter(characterId2, playerId);

        List<GameCharacter> characters = dao.getCharacters(playerId);
        assertThat(characters.size(), equalTo(2));
    }

    @Test
    void addCharacterAddsCharacter() {
        GameCharacter characterToAdd = new GameCharacter();
        characterToAdd.setId(RandomUtils.insecure().randomLong(200, 300));
        characterToAdd.setName(RandomStringUtils.insecure().nextAlphabetic(17));
        characterToAdd.setDescription(RandomStringUtils.insecure().nextAlphabetic(22));
        characterToAdd.setCreated(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        characterToAdd.setLastModified(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        characterToAdd.setLastUsed(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        characterToAdd.setUseCount(RandomUtils.insecure().randomLong(400, 500));
        characterToAdd.setLocationId(RandomUtils.insecure().randomLong(500, 600));
        characterToAdd.setUniverseId(RandomUtils.insecure().randomLong(600, 700));
        characterToAdd.setPlayerId(RandomUtils.insecure().randomLong(700, 800));

        dao.createCharacter(characterToAdd);

        GameCharacter retrieved = dao.getCharacter(characterToAdd.getId());
        assertThat(retrieved.getId(), equalTo(characterToAdd.getId()));
        assertThat(retrieved.getName(), equalTo(characterToAdd.getName()));
        assertThat(retrieved.getDescription(), equalTo(characterToAdd.getDescription()));
        assertThat(retrieved.getCreated(), equalTo(characterToAdd.getCreated()));
        assertThat(retrieved.getLastModified(), equalTo(characterToAdd.getLastModified()));
        assertThat(retrieved.getLastUsed(), equalTo(characterToAdd.getLastUsed()));
        assertThat(retrieved.getUseCount(), equalTo(characterToAdd.getUseCount()));
        assertThat(retrieved.getLocationId(), equalTo(characterToAdd.getLocationId()));
        assertThat(retrieved.getUniverseId(), equalTo(characterToAdd.getUniverseId()));
        assertThat(retrieved.getPlayerId(), equalTo(characterToAdd.getPlayerId()));
    }

    private void insertCharacter(long characterId, long playerId) {
        insertCharacter(characterId, RandomUtils.insecure().randomLong(100, 200), RandomStringUtils.insecure().nextAlphabetic(17), playerId);
    }

    private void insertCharacter(long characterId, long universeId, String name) {
        insertCharacter(characterId, universeId, name, RandomUtils.insecure().randomLong(100, 200));
    }

    private void insertCharacter(long characterId, long universeId, String name, long playerId) {
        String sql = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId, playerId) "
                + "values (" + characterId + ", '" + name + "', '" + RandomStringUtils.insecure().nextAlphabetic(16) + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0, 0, "
                + universeId + ", '" + playerId + "');";
        database.update(sql);
    }
}
