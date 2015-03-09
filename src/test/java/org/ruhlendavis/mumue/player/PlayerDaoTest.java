package org.ruhlendavis.mumue.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;

public class PlayerDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private PlayerDao dao = new PlayerDao();

    @Test
    public void successfulAuthentication() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        insertPlayer(1, loginId, password);

        assertTrue(dao.authenticate(loginId, password));
    }

    @Test
    public void playerExistsForReturnsTrue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(1, loginId, RandomStringUtils.randomAlphabetic(16));

        assertTrue(dao.playerExistsFor(loginId));
    }

    @Test
    public void playerExistsForReturnsFalse() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(1, loginId, RandomStringUtils.randomAlphabetic(16));

        assertFalse(dao.playerExistsFor(RandomStringUtils.randomAlphabetic(13)));
    }

    @Test
    public void invalidLoginFails() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        String otherLogin = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(1, loginId, password);

        assertFalse(dao.authenticate(otherLogin, password));
    }

    @Test
    public void invalidPasswordFails() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(1, loginId, password);

        assertFalse(dao.authenticate(loginId, otherPassword));
    }

    @Test
    public void getPlayerReturnsPlayer() {
        String loginId = RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(4);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(1, loginId, password);

        Player player = dao.getPlayer(loginId, password);

        assertThat(player.getLoginId(), equalTo(loginId));
    }

    @Test
    public void invalidPasswordReturnsNotFound() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(1, loginId, password);

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

    private void insertPlayer(long id, String loginId, String password) {
        String sql = "insert into players (id, loginId, password, locale, created, lastModified, lastUsed, useCount)"
                + " values (" + id + ", '" + loginId + "','" + password + "', '" + RandomStringUtils.randomAlphabetic(5) + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
