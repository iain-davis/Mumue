package org.mumue.mumue.components.space;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseAccessorProvider;

public class SpaceDao {
    private static final String GET_QUERY = "select * from spaces where id = ?";

    public Space getSpace(long spaceId) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<Space> resultSetHandler = new BeanHandler<>(Space.class, new SpaceRowProcessor());
        Space space = database.query(GET_QUERY, resultSetHandler, spaceId);
        if (space == null) {
            return new Space();
        }
        return space;
    }
}
