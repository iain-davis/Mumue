package org.ruhlendavis.mumue.runner;

import org.ruhlendavis.mumue.configuration.ConfigurationProvider;

public class InfiniteLoopRunnerFactory {
    public InfiniteLoopRunner create(InfiniteLoopBody infiniteLoopBody) {
        return new InfiniteLoopRunner(ConfigurationProvider.get(), infiniteLoopBody);
    }
}
