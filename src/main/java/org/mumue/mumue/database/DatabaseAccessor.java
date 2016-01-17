package org.mumue.mumue.database;

import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DatabaseAccessor {
    private final QueryRunner queryRunner;

    @Inject
    public DatabaseAccessor(DataSource dataSource) {
        this.queryRunner = new QueryRunner(dataSource);
    }

    DatabaseAccessor(QueryRunner queryRunner) {
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

    public int update(String query, Object... parameters) {
        try {
            return queryRunner.update(query, parameters);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
