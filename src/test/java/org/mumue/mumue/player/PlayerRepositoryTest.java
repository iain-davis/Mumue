package org.mumue.mumue.player;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.time.Instant;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

public class PlayerRepositoryTest {
    private static final Random RANDOM = new Random();
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PlayerRepository repository = new PlayerRepository(database);

    @Test
    public void getNeverReturnsNull() {
        assertThat(repository.get(RANDOM.nextInt(100) + 100), notNullValue());
    }

    @Test
    public void getReturnsPlayerById() {
        long id = RANDOM.nextInt(100);
        DatabaseHelper.insertPlayer(database, id);

        Player returned = repository.get(id);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(id));
    }

    @Test
    public void getReturnsPlayerByLoginIdAndPassword() {
        String loginId = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphabetic(13);
        DatabaseHelper.insertPlayer(database, loginId, password);

        Player returned = repository.get(loginId, password);
        assertThat(returned, notNullValue());
        assertThat(returned.getLoginId(), equalTo(loginId));
    }

    @Test
    public void savePlayer() {
        Player expected = new Player();
        expected.setId(DatabaseHelper.insertPlayer(database));
        expected.countUse();
        expected.setLoginId(RandomStringUtils.randomAlphabetic(16));
        expected.setLocale(RandomStringUtils.randomAlphabetic(5));
        expected.setLastUsed(Instant.now());
        expected.setLastModified(Instant.now());
        expected.setAdministrator(RANDOM.nextBoolean());
        repository.save(expected);

        Player player = repository.get(expected.getId());

        assertThat(player.getLoginId(), equalTo(expected.getLoginId()));
        assertThat(player.getLocale(), equalTo(expected.getLocale()));
        assertThat(player.getLastUsed(), equalTo(expected.getLastUsed()));
        assertThat(player.getLastModified(), equalTo(expected.getLastModified()));
        assertThat(player.getUseCount(), equalTo(expected.getUseCount()));
        assertThat(player.isAdministrator(), equalTo(expected.isAdministrator()));

    }
}