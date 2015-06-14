package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;

public interface Command {
    public void execute(Connection connection, String command, String arguments, Configuration configuration);
}
