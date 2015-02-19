package org.ruhlendavis.mumue.components;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class ComponentDao {
    private static final String GET_QUERY = "select * from components where id = ?";

    public Component getComponent(long id) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<Component> resultSetHandler = new BeanHandler<>(Component.class);
        try {
            Component component = database.query(GET_QUERY, resultSetHandler, id);
            if (component == null) {
                return new Component().withId(GlobalConstants.REFERENCE_NOT_FOUND);
            }
            return component;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}