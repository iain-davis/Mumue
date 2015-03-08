package org.ruhlendavis.mumue.threading;

import org.ruhlendavis.mumue.configuration.ConfigurationProvider;

public class InfiniteLoopRunnerFactory {
    public InfiniteLoopRunner create(InfiniteLoopBody infiniteLoopBody) {
        return new InfiniteLoopRunner(ConfigurationProvider.get(), infiniteLoopBody);
    }
}
