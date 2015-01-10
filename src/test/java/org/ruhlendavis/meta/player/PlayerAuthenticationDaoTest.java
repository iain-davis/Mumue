package org.ruhlendavis.meta.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;

public class PlayerAuthenticationDaoTest {
    private PlayerAuthenticationDao dao;

    @Before
    public void beforeEach() throws SQLException {
        DatabaseHelper.setupTestDatabaseWithDefaultData();
        dao = new PlayerAuthenticationDao();
    }

    @Test
    public void successfulAuthentication() {
        String login = "first";
        String password = "firstword";
        assertTrue(dao.authenticate(login, password));
    }

    @Test
    public void invalidLoginFails() {
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = "firstword";
        assertFalse(dao.authenticate(login, password));
    }

    @Test
    public void invalidPasswordFails() {
        String login = "first";
        String password = RandomStringUtils.randomAlphabetic(13);
        assertFalse(dao.authenticate(login, password));
    }
}
