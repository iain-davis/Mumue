package org.mumue.mumue.components.universe;

import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseAccessorProvider;

public class UniverseDao {
    private static final String GET_QUERY = "select * from universes where id = ?";
    private static final String GET_ALL_QUERY = "select * from universes";

    public Collection<Universe> getUniverses() {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<List<Universe>> resultSetHandler = new BeanListHandler<>(Universe.class, new UniverseRowProcessor());
        return database.query(GET_ALL_QUERY, resultSetHandler);
    }

    public Universe getUniverse(long universeId) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<Universe> resultSetHandler = new BeanHandler<>(Universe.class, new UniverseRowProcessor());
        Universe universe = database.query(GET_QUERY, resultSetHandler, universeId);
        if (universe == null) {
            return new Universe();
        }
        return universe;
    }
}
