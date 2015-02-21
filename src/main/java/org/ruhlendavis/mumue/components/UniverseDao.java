package org.ruhlendavis.mumue.components;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class UniverseDao {
    private static final String GET_QUERY = "select * from universes where id = ?";
    private static final String GET_ALL_QUERY = "select * from universes";

    public Collection<Universe> getUniverses() {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<List<Universe>> resultSetHandler = new BeanListHandler<>(Universe.class, new UniverseRowProcessor());

        try {
            return database.query(GET_ALL_QUERY, resultSetHandler);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Universe getUniverse(long universeId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<Universe> resultSetHandler = new BeanHandler<>(Universe.class, new UniverseRowProcessor());
        try {
            Universe universe = database.query(GET_QUERY, resultSetHandler, universeId);
            if (universe == null) {
                return new Universe();
            }
            return universe;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }
}
