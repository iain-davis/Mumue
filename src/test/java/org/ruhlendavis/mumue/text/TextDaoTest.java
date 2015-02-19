package org.ruhlendavis.mumue.text;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;

public class TextDaoTest {
    private final TextDao textDao = new TextDao();
    @Test
    public void getText() throws SQLException {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(25);

        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
        queryRunner.update("insert into text (locale, name, text) values (?, ?, ?)", locale, TextName.Welcome.toString(), text);

        assertThat(textDao.getText(TextName.Welcome, locale), equalTo(text));
    }
}
