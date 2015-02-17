package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;

public interface ConnectionStage {
    public ConnectionStage execute(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration);
}
