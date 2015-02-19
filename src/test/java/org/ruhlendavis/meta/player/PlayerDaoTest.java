package org.ruhlendavis.meta.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;
import org.ruhlendavis.meta.importer.GlobalConstants;

public class PlayerDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private PlayerDao dao = new PlayerDao();

    @Test
    public void successfulAuthentication() {
        String login = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        insertPlayer(login, password);

        assertTrue(dao.authenticate(login, password));
    }

    @Test
    public void invalidLoginFails() {
        String login = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(16);
        String otherLogin = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(login, password);

        assertFalse(dao.authenticate(otherLogin, password));
    }

    @Test
    public void invalidPasswordFails() {
        String login = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(login, password);

        assertFalse(dao.authenticate(login, otherPassword));
    }

    @Test
    public void getPlayerReturnsPlayer() {
        long id = RandomUtils.nextInt(100, 200);
        String loginId = RandomStringUtils.randomAlphabetic(3);
        String password = RandomStringUtils.randomAlphabetic(4);
        String name = RandomStringUtils.randomAlphabetic(17);
        long locationId = RandomUtils.nextInt(200, 300);
        insertPlayer(id, loginId, password, name, locationId);

        Player player = dao.getPlayer(loginId, password);

        assertThat(player.getId(), equalTo(id));
        assertThat(player.getLoginId(), equalTo(loginId));
        assertThat(player.getName(), equalTo(name));
        assertThat(player.getLocationId(), equalTo(locationId));
    }

    @Test
    public void invalidPasswordReturnsNotFound() {
        String login = RandomStringUtils.randomAlphabetic(17);
        String password = RandomStringUtils.randomAlphabetic(13);
        String otherPassword = RandomStringUtils.randomAlphabetic(15);
        insertPlayer(login, password);

        Player player = dao.getPlayer(login, otherPassword);

        assertThat(player.getId(), equalTo(GlobalConstants.REFERENCE_NOT_FOUND));
    }

    private void insertPlayer(String login, String password) {
        insertPlayer(RandomUtils.nextInt(100, 200), login, password,
                RandomStringUtils.randomAlphabetic(4), RandomUtils.nextInt(200, 300));
    }

    private void insertPlayer(long id, String login, String password, String name, long locationId) {
        String sql = "insert into players (id, loginId, name, password, locationId)"
                + " values (" + id + ", '" + login + "', '" + name + "', '" + password + "', " + locationId + ");";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
