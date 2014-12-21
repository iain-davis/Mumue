package org.ruhlendavis.meta.text;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class TextDao {
    public static final String QUERY_TEXT = "select text from text where locale = ? and name = ?";
    public static final String TEXT_COLUMN = "text";
    private final QueryRunner queryRunner;

    public TextDao(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public String getText(String locale, TextName textName) {
        ResultSetHandler resultSetHandler = new ScalarHandler<>(TEXT_COLUMN);
        try {
            String text = (String)queryRunner.query(QUERY_TEXT, resultSetHandler, locale, textName.toString());
            return StringUtils.defaultString(text);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
