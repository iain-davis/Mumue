package org.mumue.mumue.text;

import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.mumue.mumue.database.DatabaseAccessor;

public class TextDao {
    public static final String QUERY_TEXT = "select text from text where name = ? and locale = ?";
    public static final String TEXT_COLUMN = "text";
    private final DatabaseAccessor database;

    @Inject
    public TextDao(DatabaseAccessor database) {
        this.database = database;
    }

    public String getText(TextName textName, String locale) {
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(TEXT_COLUMN);
        return database.query(QUERY_TEXT, resultSetHandler, textName.toString(), locale);
    }
}
