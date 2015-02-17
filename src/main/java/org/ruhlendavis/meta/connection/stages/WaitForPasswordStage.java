package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;

public class WaitForPasswordStage implements ConnectionStage {
    @Override
    public ConnectionStage execute(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration) {
        if (inputQueue.size() < 2) {
            return this;
        }
        return new PlayerAuthenticationStage();
    }
}
