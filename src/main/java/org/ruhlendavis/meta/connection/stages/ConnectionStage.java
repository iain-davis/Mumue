package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;

public interface ConnectionStage {
    public ConnectionStage execute(Collection<String> inputQueue, Collection<String> outputQueue, Configuration configuration);
}
