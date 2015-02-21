package org.ruhlendavis.mumue.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        insertPlayer(loginId, password);

        assertTrue(dao.authenticate(loginId, password));
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
    public void getPlayerReturnsPlayer() {
        String loginId = RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(4);
        String name = RandomStringUtils.randomAlphabetic(17);
        insertPlayer(loginId, password);

        Player player = dao.getPlayer(loginId, password);

        assertThat(player.getLoginId(), equalTo(loginId));
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

    private void insertPlayer(String loginId, String password) {
        String sql = "insert into players (loginId, password, created, lastModified, lastUsed, useCount)"
                + " values ('" + loginId + "','" + password + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
