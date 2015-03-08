package org.ruhlendavis.mumue.database;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DatabaseAccessor {
    private final QueryRunner queryRunner;

    public DatabaseAccessor(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public <T> T query(String query, ResultSetHandler<T> resultSetHandler, Object... parameters) {
        try {
            return queryRunner.query(query, resultSetHandler, parameters);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public int update(String query) {
        try {
            return queryRunner.update(query);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}