package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;

public class NoOperationStage implements ConnectionStage {
    @Override
    public ConnectionStage execute(Collection<String> inputQueue, Collection<String> outputQueue, Configuration configuration) {
        return this;
    }
}
