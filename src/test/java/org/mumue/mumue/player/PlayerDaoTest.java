package org.mumue.mumue.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.importer.GlobalConstants;

public class PlayerDaoTest {
    private static final Random RANDOM = new Random();
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PlayerDao dao = new PlayerDao(database, new PlayerRepository(database));

    @Test
    public void successfulAuthentication() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        insertPlayer(loginId, password);

        assertTrue(dao.authenticate(loginId, password));
    }

    @Test
    public void playerExistsForReturnsTrue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(loginId, RandomStringUtils.randomAlphabetic(16));

        assertTrue(dao.playerExistsFor(loginId));
    }

    @Test
    public void playerExistsForReturnsFalse() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(loginId, RandomStringUtils.randomAlphabetic(16));

        assertFalse(dao.playerExistsFor(RandomStringUtils.randomAlphabetic(13)));
    }

    @Test
    public void invalidLoginFails() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        String otherLogin = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(loginId, password);

        assertFalse(dao.authenticate(otherLogin, password));
    }

    @Test
    public void invalidPasswordFails() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(loginId, password);

        assertFalse(dao.authenticate(loginId, otherPassword));
    }

    @Test
    public void getPlayerByLoginReturnsPlayer() {
        long id = RANDOM.nextInt(1000) + 10;
        String loginId = RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(4);
        insertPlayer(id, loginId, password);


        Player player = dao.getPlayer(loginId, password);

        assertThat(player.getId(), equalTo(id));
        assertThat(player.getLoginId(), equalTo(loginId));
    }

    @Test
    public void getPlayerByLoginWhenDoesNotExist() {
        String loginId = RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(4);
        insertPlayer(loginId, password);

        Player player = dao.getPlayer(RandomStringUtils.randomAlphabetic(4), password);

        assertThat(player.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    @Test
    public void invalidPasswordReturnsNotFound() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(loginId, password);

        Player player = dao.getPlayer(loginId, otherPassword);

        assertThat(player.getLoginId(), equalTo(""));
    }

    @Test
    public void addPlayer() {
        Player expected = new PlayerBuilder().withId(1)
                .withLocale(RandomStringUtils.randomAlphabetic(13))
                .withLoginId(RandomStringUtils.randomAlphabetic(14))
                .build();
        String password = RandomStringUtils.randomAlphabetic(12);

        dao.createPlayer(expected, password);

        Player player = dao.getPlayer(expected.getLoginId(), password);
        assertReflectionEquals(expected, player);
    }

    private void insertPlayer(String loginId, String password) {
        insertPlayer(RANDOM.nextInt(10000000), loginId, password);
    }

    private void insertPlayer(long id, String loginId, String password) {
        String sql = "insert into players (id, loginId, password, locale, created, lastModified, lastUsed, useCount)"
                + " values (" + id + ", '" + loginId + "','" + password + "', '" + RandomStringUtils.randomAlphabetic(5) + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        database.update(sql);
    }
}
