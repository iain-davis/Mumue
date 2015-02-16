package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;

public class WaitForLoginIdStage implements ConnectionStage {
    @Override
    public ConnectionStage execute(Collection<String> inputQueue, Collection<String> outputQueue, Configuration configuration) {
        if (inputQueue.isEmpty()) {
            return new WaitForLoginIdStage();
        }
        return new NoOperationStage();
    }
}
