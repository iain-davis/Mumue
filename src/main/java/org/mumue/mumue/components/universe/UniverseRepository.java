package org.mumue.mumue.components.universe;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.importer.GlobalConstants;

public class UniverseRepository {
    private static final String GET_QUERY = "select * from universes where id = ?";
    private static final String GET_ALL_QUERY = "select * from universes";
    private final DatabaseAccessor database;

    @Inject
    public UniverseRepository(DatabaseAccessor database) {
        this.database = database;
    }

    public Collection<Universe> getUniverses() {
        ResultSetHandler<List<Universe>> resultSetHandler = new BeanListHandler<>(Universe.class, new UniverseRowProcessor());
        return database.query(GET_ALL_QUERY, resultSetHandler);
    }

    public Universe getUniverse(long universeId) {
        ResultSetHandler<Universe> resultSetHandler = new BeanHandler<>(Universe.class, new UniverseRowProcessor());
        Universe universe = database.query(GET_QUERY, resultSetHandler, universeId);
        if (universe == null) {
            return new Universe();
        }
        return universe;
    }

    public void add(Universe universe) {
        if (universe.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            throw new RuntimeException();
        }
        database.update("insert into universes (id, name, description, created, lastUsed, lastModified, useCount) values (?, ?, ?, ?, ?, ?, ?)",
                universe.getId(), universe.getName(), universe.getDescription(),
                Timestamp.from(universe.getCreated()), Timestamp.from(universe.getLastUsed()), Timestamp.from(universe.getLastModified()),
                universe.getUseCount()
        );
    }

    public Universe save(Universe universe) {
        updateUniverse(universe);
        return universe;
    }

    private void updateUniverse(Universe universe) {
        database.update("update universes SET id=?, name=?, description=?, created=?, lastUsed=?, lastModified=?, useCount=?",
                universe.getId(), universe.getName(), universe.getDescription(),
                Timestamp.from(universe.getCreated()), Timestamp.from(universe.getLastUsed()), Timestamp.from(universe.getLastModified()),
                universe.getUseCount()
        );
    }
}
