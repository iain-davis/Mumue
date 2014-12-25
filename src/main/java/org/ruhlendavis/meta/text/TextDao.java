package org.ruhlendavis.meta.text;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import org.ruhlendavis.meta.database.QueryRunnerProvider;

public class TextDao {
    public static final String QUERY_TEXT = "select text from text where locale = ? and name = ?";
    public static final String TEXT_COLUMN = "text";

    public String getText(String locale, TextName textName) {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(TEXT_COLUMN);
        try {
            String text = (String)queryRunner.query(QUERY_TEXT, resultSetHandler, locale, textName.toString());
            return StringUtils.defaultString(text);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
