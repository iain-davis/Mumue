package org.ruhlendavis.mumue.text;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.mumue.database.DatabaseAccessor;
import org.ruhlendavis.mumue.database.DatabaseAccessorProvider;

public class TextDao {
    public static final String QUERY_TEXT = "select text from text where name = ? and locale = ?";
    public static final String TEXT_COLUMN = "text";

    public String getText(TextName textName, String locale) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(TEXT_COLUMN);
        return database.query(QUERY_TEXT, resultSetHandler, textName.toString(), locale);
    }
}
