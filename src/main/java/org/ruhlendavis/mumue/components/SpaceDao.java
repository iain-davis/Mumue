package org.ruhlendavis.mumue.components;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class SpaceDao {
    private static final String GET_QUERY = "select * from spaces where id = ?";

    public Space getSpace(long spaceId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<Space> resultSetHandler = new BeanHandler<>(Space.class, new SpaceRowProcessor());
        try {
            Space space = database.query(GET_QUERY, resultSetHandler, spaceId);
            if (space == null) {
                return new Space();
            }
            return space;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
