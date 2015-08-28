package org.mumue.mumue.text;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.database.DatabaseAccessor;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextDaoTest {
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final TextDao textDao = new TextDao(database);

    @Test
    public void getText() throws SQLException {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(25);

        database.update("insert into text (locale, name, text) values (?, ?, ?)", locale, TextName.Welcome.toString(), text);

        assertThat(textDao.getText(TextName.Welcome, locale), equalTo(text));
    }
}
