package org.ruhlendavis.mumue.components;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class UniverseDao {
    private static final String GET_ALL_QUERY = "select * from universes";

    public Collection<Universe> getUniverses() {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<List<Universe>> resultSetHandler = new BeanListHandler<>(Universe.class, new UniverseRowProcessor());

        try {
            Collection<Universe> universes = database.query(GET_ALL_QUERY, resultSetHandler);
            return universes;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
