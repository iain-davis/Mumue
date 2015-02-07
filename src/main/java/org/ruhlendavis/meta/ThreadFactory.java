package org.ruhlendavis.meta;

import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

public class ThreadFactory {
    public Thread create(Runnable runnable, String name) {
        return new Thread(runnable, name);
    }

    public Thread create(Runnable runnable) {
        return new Thread(runnable);
    }
}
