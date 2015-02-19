package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;

public interface ConnectionStage {
    public ConnectionStage execute(Connection connection, Configuration configuration);
}
