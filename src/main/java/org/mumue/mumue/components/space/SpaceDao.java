package org.mumue.mumue.components.space;

import jakarta.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.mumue.mumue.database.DatabaseAccessor;

public class SpaceDao {
    private static final String GET_QUERY = "select * from spaces where id = ?";
    private final DatabaseAccessor database;

    @Inject
    public SpaceDao(DatabaseAccessor database) {
        this.database = database;
    }

    public Space getSpace(long spaceId) {
        ResultSetHandler<Space> resultSetHandler = new BeanHandler<>(Space.class, new SpaceRowProcessor());
        Space space = database.query(GET_QUERY, resultSetHandler, spaceId);
        if (space == null) {
            return new Space();
        }
        return space;
    }
}
