package org.mumue.mumue.configuration;

import java.util.List;
import jakarta.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mumue.mumue.database.DatabaseAccessor;

public class PortConfigurationRepository {
    private static final String GET_ALL_QUERY = "select port,type,supportsMenus from configuration_ports;";
    private final DatabaseAccessor database;

    @Inject
    public PortConfigurationRepository(DatabaseAccessor database) {
        this.database = database;
    }

    public List<PortConfiguration> getAll() {
        ResultSetHandler<List<PortConfiguration>> resultSetHandler = new BeanListHandler<>(PortConfiguration.class);
        return database.query(GET_ALL_QUERY, resultSetHandler);
    }
}
