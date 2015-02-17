package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;

public class NoOperationStage implements ConnectionStage {
    @Override
    public ConnectionStage execute(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration) {
        return this;
    }
}
