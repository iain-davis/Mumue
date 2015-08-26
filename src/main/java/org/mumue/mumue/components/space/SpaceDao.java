package org.mumue.mumue.components.space;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseAccessorProvider;

public class SpaceDao {
    private static final String GET_QUERY = "select * from spaces where id = ?";
    private final DatabaseAccessor database;

    public SpaceDao(DatabaseAccessor database) {
        this.database = database;
    }

    public SpaceDao() {
        this(DatabaseAccessorProvider.get());
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
