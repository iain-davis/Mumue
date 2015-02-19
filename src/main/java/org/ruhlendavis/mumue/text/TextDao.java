package org.ruhlendavis.mumue.text;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class TextDao {
    public static final String QUERY_TEXT = "select text from text where name = ? and locale = ?";
    public static final String TEXT_COLUMN = "text";

    public String getText(TextName textName, String locale) {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(TEXT_COLUMN);
        try {
            return queryRunner.query(QUERY_TEXT, resultSetHandler, textName.toString(), locale);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
