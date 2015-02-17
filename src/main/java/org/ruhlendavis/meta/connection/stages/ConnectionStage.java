package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.Connection;

public interface ConnectionStage {
    public ConnectionStage execute(Connection connection, Configuration configuration);
}
