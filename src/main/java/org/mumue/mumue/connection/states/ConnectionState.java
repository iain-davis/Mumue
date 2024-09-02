package org.mumue.mumue.connection.states;

import jakarta.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

@Singleton
public interface ConnectionState {
    ConnectionState execute(Connection connection, ApplicationConfiguration configuration);
}
