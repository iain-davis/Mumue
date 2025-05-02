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
import org.mumue.mumue.importer.GlobalConstants;

public class PlayerRepositoryTest {
    private static final Random RANDOM = new Random();
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PlayerRepository repository = new PlayerRepository(database);

    @Test
    public void getNeverReturnsNull() {
        assertThat(repository.get(RANDOM.nextInt(100) + 100), notNullValue());
    }

    @Test
    public void getByIdReturnsPlayer() {
        long id = RANDOM.nextInt(100);
        DatabaseHelper.insertPlayer(database, id);

        Player returned = repository.get(id);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(id));
    }

    @Test
    public void getByIdReturnsUnknownPlayer() {
        long id = RANDOM.nextInt(100);
        DatabaseHelper.insertPlayer(database, id);

        Player returned = repository.get(id * 2);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdReturnsPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        DatabaseHelper.insertPlayer(database, loginId);

        Player returned = repository.get(loginId);

        assertThat(returned, notNullValue());
        assertThat(returned.getLoginId(), equalTo(loginId));
    }

    @Test
    public void getByLoginIdAndIncorrectReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(7);
        DatabaseHelper.insertPlayer(database);

        Player returned = repository.get(loginId);

        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdAndIsNullReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(7);
        DatabaseHelper.insertPlayer(database);

        Player returned = repository.get(null);

        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdAndPasswordReturnsPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        DatabaseHelper.insertPlayer(database, loginId, password);

        Player returned = repository.get(loginId, password);
        assertThat(returned, notNullValue());
        assertThat(returned.getLoginId(), equalTo(loginId));
    }

    @Test
    public void getByLoginIdAndPasswordLoginIdIncorrectReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        DatabaseHelper.insertPlayer(database, loginId, password);

        Player returned = repository.get("SOME_OTHER", password);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdAndPasswordLoginIdIsNullReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        DatabaseHelper.insertPlayer(database, loginId, password);

        Player returned = repository.get(null, password);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdAndPasswordPasswordIncorrectReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        DatabaseHelper.insertPlayer(database, loginId);

        Player returned = repository.get(loginId, password);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void getByLoginIdAndPasswordPasswordIsNullReturnsUnknownPlayer() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(16);
        DatabaseHelper.insertPlayer(database, loginId);

        Player returned = repository.get(loginId, null);
        assertThat(returned, notNullValue());
        assertThat(returned.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void addInsertsPlayerIntoDatabase() {
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        Player expected = createPlayer();

        repository.add(expected, password);

        Player player = repository.get(expected.getLoginId(), password);

        assertThat(player.getLoginId(), equalTo(expected.getLoginId()));
        assertThat(player.getLocale(), equalTo(expected.getLocale()));
        assertThat(player.getLastUsed(), equalTo(expected.getLastUsed()));
        assertThat(player.getLastModified(), equalTo(expected.getLastModified()));
        assertThat(player.getUseCount(), equalTo(expected.getUseCount()));
        assertThat(player.isAdministrator(), equalTo(expected.isAdministrator()));
    }

    @Test
    public void savePlayer() {
        Player expected = createPlayer();
        repository.save(expected);

        Player player = repository.get(expected.getId());

        assertThat(player.getLocale(), equalTo(expected.getLocale()));
        assertThat(player.getLastUsed(), equalTo(expected.getLastUsed()));
        assertThat(player.getLastModified(), equalTo(expected.getLastModified()));
        assertThat(player.getUseCount(), equalTo(expected.getUseCount()));
        assertThat(player.isAdministrator(), equalTo(expected.isAdministrator()));

    }

    public Player createPlayer() {
        Player expected = new Player();
        expected.setId(DatabaseHelper.insertPlayer(database));
        expected.countUse();
        expected.setLoginId(RandomStringUtils.insecure().nextAlphabetic(16));
        expected.setLocale(RandomStringUtils.insecure().nextAlphabetic(5));
        expected.setLastUsed(Instant.now());
        expected.setLastModified(Instant.now());
        expected.setAdministrator(RANDOM.nextBoolean());
        return expected;
    }
}
