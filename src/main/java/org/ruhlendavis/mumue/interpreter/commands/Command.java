package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;

public interface Command {
    public void execute(Connection connection, String command, String arguments, Configuration configuration);
}
