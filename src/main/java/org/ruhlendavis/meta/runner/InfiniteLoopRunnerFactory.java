package org.ruhlendavis.meta.runner;

import org.ruhlendavis.meta.configuration.ConfigurationProvider;

public class InfiniteLoopRunnerFactory {
    public InfiniteLoopRunner create(InfiniteLoopBody infiniteLoopBody) {
        return new InfiniteLoopRunner(ConfigurationProvider.get(), infiniteLoopBody);
    }
}
