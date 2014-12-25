package org.ruhlendavis.meta.text;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;

public class TextDaoTest {
    private final TextDao textDao = new TextDao();
    @Test
    public void getTextNeverReturnsNull() {
        DatabaseHelper.setupTestDatabaseWithDefaultData();
        assertNotNull(textDao.getText("", TextName.Welcome));
    }

    @Test
    public void getText() throws SQLException {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(25);

        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithDefaultData();
        queryRunner.update("insert into text (locale, name, text) values (?, ?, ?)", locale, TextName.Welcome.toString(), text);

        assertThat(textDao.getText(locale, TextName.Welcome), equalTo(text));
    }
}
