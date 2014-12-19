package org.ruhlendavis.meta.configuration.database;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ConnectionSourceProvider {
    public ConnectionSource get(String uri, String username, String password) {
        try {
            return new JdbcConnectionSource(uri, username, password);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
